package pl.camp.it.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.camp.it.model.Promotion;
import pl.camp.it.service.IPromotionService;
import pl.camp.it.service.IReservationService;
import pl.camp.it.session.SessionObject;


import java.util.List;

@Controller
public class CommonController {
    @Autowired
    SessionObject sessionObject;

    @Autowired
    IPromotionService promotionService;

    @GetMapping(value = "/main")
    public String showMainPage(Model model) {
        List<Promotion> promotions = this.promotionService.getDistinctedPromotions();
        model.addAttribute("promotions", promotions);
        return "main";
    }
    @GetMapping(value="/")
    public String redirectToMain() {
        return "redirect:/login";
    }

    @GetMapping(value="/restorerMain")
    public String showRestorerMain(Model model) {
        model.addAttribute("user", sessionObject.getUser());
        return "restorerMain";
    }

    @GetMapping(value="/logout")
    public String logout() {
        sessionObject.setLogged(false);
        return "redirect:/login";
    }

}
