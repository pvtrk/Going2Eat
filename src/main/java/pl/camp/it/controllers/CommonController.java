package pl.camp.it.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {
    @GetMapping(value = "/main")
    public String showMainPage() {
        return "main";
    }
}
