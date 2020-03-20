package pl.camp.it.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.model.Promotion;
import pl.camp.it.model.Reservation;
import pl.camp.it.service.IPromotionService;
import pl.camp.it.service.IReservationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class CommonController {
    @Autowired
    IReservationService reservationService;
    @Autowired
    IPromotionService promotionService;

    @GetMapping(value = "/main")
    public String showMainPage(Model model) {
        List<Promotion> promotions = this.promotionService.getPromotionsSortedByDate();
        model.addAttribute("promotions", promotions);
        return "main";
    }
    @GetMapping(value="/")
    public String redirectToMain() {
        return "redirect:/main";
    }
    @GetMapping(value = "/reservation")
    public String testReservation() {
        return "reservation";
    }

}
