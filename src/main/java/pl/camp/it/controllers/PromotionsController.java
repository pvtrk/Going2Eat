package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.model.Promotion;
import pl.camp.it.model.PromotionStatus;
import pl.camp.it.model.Restaurant;
import pl.camp.it.service.IPromotionService;
import pl.camp.it.service.IRestaurantService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Controller
public class PromotionsController {
    @Autowired
    IRestaurantService restaurantService;
    @Autowired
    IPromotionService promotionService;

    @GetMapping(value="/promotions")
    public String listPromotions(Model model) {
        model.addAttribute("promotions", this.promotionService.getPromotionsSortedByDate());
        return "promotions";
    }
    @GetMapping(value="/addPromotion/{id}")
    public String showPromotionsAddingFormula(@PathVariable int id, Model model) {
        Restaurant rest = this.restaurantService.getRestaurantById(id);
        model.addAttribute("promotion", new Promotion());
        model.addAttribute("restaurant" , rest);
        return "addPromotion";
    }

    @PostMapping(value="/addPromotion/{id}")
    public String addingPromotionAction(@PathVariable int id, Model model, @ModelAttribute Promotion promotion,
                                        @RequestParam String date1, @RequestParam String date2) {

        promotion.setRestaurant(this.restaurantService.getRestaurantById(id));
        promotion.setStatus(PromotionStatus.ACTIVE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        promotion.setStartDate(LocalDate.parse(date1, formatter));
        promotion.setEndDate(LocalDate.parse(date2, formatter));
        this.promotionService.persistPromotion(promotion);
        return "addPromotion";
    }

}
