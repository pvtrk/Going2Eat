package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.model.Promotion;
import pl.camp.it.model.PromotionStatus;
import pl.camp.it.model.Restaurant;
import pl.camp.it.service.IPromotionService;
import pl.camp.it.service.IRestaurantService;
import pl.camp.it.service.impl.PageService;
import pl.camp.it.utils.RegexChecker;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
public class PromotionsController {
    @Autowired
    IRestaurantService restaurantService;
    @Autowired
    RegexChecker regexChecker;
    @Autowired
    PageService pageService;
    @Autowired
    IPromotionService promotionService;

    @GetMapping(value = "/promotions")
    public String listPromotions(Model model,
                                 @RequestParam Optional<Integer> size,
                                 @RequestParam Optional<Integer> page) {
        int pageSize = size.orElse(6);
        int currentPage = page.orElse(1);
        int previousPage = 1;
        int nextPage = currentPage + 1;

        List<Promotion> promList = promotionService.getPromotionsSortedByDate();
        Page<Promotion> promotionPage =
                pageService.findPaginated(PageRequest.of(currentPage - 1, pageSize), promList);
        model.addAttribute("promotionsPage", promotionPage);
        int totalPages = promotionPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers =
                    IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        if (currentPage > 1) {
            previousPage = currentPage - 1;
        }
        if (currentPage == promotionPage.getTotalPages()) {
            nextPage = currentPage;

        }
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("previousPage", previousPage);

        return "promotions";
    }

    @GetMapping(value = "/addPromotion/{id}")
    public String showPromotionsAddingFormula(@PathVariable int id, Model model) {
        Restaurant rest = this.restaurantService.getRestaurantById(id);
        model.addAttribute("promotion", new Promotion());
        model.addAttribute("restaurant", rest);
        return "addPromotion";
    }

    @PostMapping(value = "/addPromotion/{id}")
    public String addingPromotionAction(@PathVariable int id, Model model, @ModelAttribute Promotion promotion,
                                        @RequestParam String date1, @RequestParam String date2) {
        Restaurant restaurant = this.restaurantService.getRestaurantById(id);
        if (restaurant != null && promotion != null) {
            promotion.setRestaurant(restaurant);
            if (regexChecker.checkInput(date1, regexChecker.getDateRegexp()) &&
                    regexChecker.checkInput(date2, regexChecker.getDateRegexp()) &&
                    promotionService.createPromotion(promotion, date1, date2)) {
                model.addAttribute("message", "Dodano promocję!");
            } else {
                model.addAttribute("message", "Nie udało się dodać promocji.");
            }
        }
        model.addAttribute("restaurant", restaurant);

        return "addPromotion";
    }
    @GetMapping(value= "/deletePromotion/{id}")
    public String deletePromotionForm(@PathVariable int id, Model model) {
        List<Promotion> promotions = promotionService.getPromotionsByRestaurantId(id);
        model.addAttribute("promotions", promotions);

        return "deletePromotion";
    }

    @PostMapping(value ="/delete/{id}")
    public String deletePromotionAction(@PathVariable int id) {
        Promotion promotion = promotionService.getPromotionById(id);
        if (promotion != null) {
            promotion.setStatus(PromotionStatus.OFF);
            promotionService.persistPromotion(promotion);
        }
        return "redirect:/myRestaurants";
    }
}

