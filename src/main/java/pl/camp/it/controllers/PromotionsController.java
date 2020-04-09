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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
public class PromotionsController {
    @Autowired
    IRestaurantService restaurantService;
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
        if (promotion != null) {
            promotion.setRestaurant(restaurant);
            if (promotionService.validatePromotionInput(promotion)) {
                promotion.setStatus(PromotionStatus.ACTIVE);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                promotion.setStartDate(LocalDate.parse(date1, formatter));
                promotion.setEndDate(LocalDate.parse(date2, formatter));
                this.promotionService.persistPromotion(promotion);
                model.addAttribute("restaurant", restaurant);

            }
        }
        model.addAttribute("restaurant", restaurant);
        return "addPromotion";
    }
}

