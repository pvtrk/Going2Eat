package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.model.Register;
import pl.camp.it.model.UserRole;
import pl.camp.it.service.IUserService;
import pl.camp.it.session.SessionObject;

@Controller
public class UserController {
    @Autowired
    SessionObject sessionObject;
    @Autowired
    IUserService userService;

    @GetMapping(value="/login")
    public String showLoginScreen() {
        return "login";
    }

    @PostMapping(value="/login")
    public String doLoginAction(@RequestParam String login, @RequestParam String password) {
        if(this.userService.authenticateUser(login, password)) {
            sessionObject.setUser(this.userService.getUserByLogin(login));
            sessionObject.setLogged(true);
            if (sessionObject.getUser().getRole().equals(UserRole.USER)) {
                return "redirect:/main";
            } else if (sessionObject.getUser().getRole().equals(UserRole.RESTORER)) {
                return "redirect:/restorerMain";
            } else {
                return "redirect:/adminMenu";
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
