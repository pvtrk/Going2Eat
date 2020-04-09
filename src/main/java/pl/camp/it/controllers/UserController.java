package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.model.Register;
import pl.camp.it.model.UserRole;
import pl.camp.it.service.IUserService;
import pl.camp.it.session.SessionObject;
import pl.camp.it.utils.RegexChecker;

@Controller
public class UserController {
    @Autowired
    SessionObject sessionObject;
    @Autowired
    IUserService userService;
    @Autowired
    RegexChecker regexChecker;

    @GetMapping(value="/login")
    public String showLoginScreen() {
        return "login";
    }

    @PostMapping(value="/login")
    public String doLoginAction(@RequestParam String login, @RequestParam String password, Model model) {
        model.addAttribute("alert", null);
        if(regexChecker.checkInput(login, regexChecker.getLoginRegex()) &&
        regexChecker.checkInput(password, regexChecker.getPassRegex())) {

        if(userService.authenticateUser(login, password)) {
            sessionObject.setUser(userService.getUserByLogin(login));
            sessionObject.setLogged(true);
            if (sessionObject.getUser().getRole().equals(UserRole.USER)) {
                return "redirect:/main";
            } else if (sessionObject.getUser().getRole().equals(UserRole.RESTORER)) {
                return "redirect:/restorerMain";
            } else if (sessionObject.getUser().getRole().equals(UserRole.ADMIN)) {
                return "redirect:/adminMenu";
            }
        } else {
            model.addAttribute("alert", "Nieudana próba zalogowania. Spróbuj jeszcze raz");
        }
        } return "login";
    }

    @GetMapping(value="/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("register", new Register());

        return "register";
    }


    @PostMapping(value="/register")
    public String registerUser(@ModelAttribute Register register) {
        boolean validateRegister = userService.checkRegister(register);
        if(validateRegister) {
            userService.registerUser(register);
            sessionObject.setLogged(true);
            sessionObject.setUser(userService.getUserByLogin(register.getLogin()));
            return "redirect:/main";
        }
        return "register";
    }

}
