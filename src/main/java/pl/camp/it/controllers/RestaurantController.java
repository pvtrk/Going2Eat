package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.model.Restaurant;
import pl.camp.it.model.RestaurantStatus;
import pl.camp.it.service.IRestaurantService;
import pl.camp.it.service.IUserService;
import pl.camp.it.service.impl.PageService;
import pl.camp.it.session.SessionObject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class RestaurantController {
    @Autowired
    PageService pageService;
    @Autowired
    IRestaurantService restaurantService;
    @Autowired
    IUserService userService;
    @Autowired
    SessionObject sessionObject;

    @GetMapping(value ="/restaurants")
    public String showAllTheRestaurants(Model model,
    @RequestParam Optional<Integer> size,
    @RequestParam Optional<Integer> page) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(6);
        int previousPage = 1;
        int nextPage = currentPage + 1;

        List<Restaurant> restaurantList = restaurantService.getActiveRestaurants();
        Page<Restaurant> restaurantPage = pageService.findPaginated
                (PageRequest.of(currentPage - 1, pageSize), restaurantList);
       // Page<Restaurant> restaurantPage = restaurantService.findPaginated
                //(PageRequest.of(currentPage - 1, pageSize), restaurantList);
         model.addAttribute("restaurantPage", restaurantPage);

         int totalPages = restaurantPage.getTotalPages();
         if (totalPages > 0) {
             List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                     .boxed().collect(Collectors.toList());
             model.addAttribute("pageNumbers", pageNumbers);
         }
         if (currentPage > 1) {
             previousPage = currentPage - 1;
         }
         if (currentPage == restaurantPage.getTotalPages()) {
             nextPage = currentPage;
         }
         model.addAttribute("nextPage", nextPage);
         model.addAttribute("previousPage", previousPage);
         return "restaurants";
    }
    @GetMapping(value="/moreInfo/{id}")
    public String moreInfoAboutRestaurant(@PathVariable int id, Model model) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        model.addAttribute("restaurant", restaurant);
        return "moreInfo";
    }
    @GetMapping(value="/addToFavourite/{id}")
    public String addRestaurantToFavourites(@PathVariable int id) {
        if(!this.restaurantService.isAlreadyFavourite(id)) {
            this.restaurantService.addFavouriteRestaurant(sessionObject.getUser().getId(), id);
        }
        return "redirect:/restaurants";
    }

    @GetMapping(value="/myFavourite")
    public String showFavourites(Model model,
                                 @RequestParam Optional<Integer> page,
                                 @RequestParam Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(6);
        int previousPage = 1;
        int nextPage = currentPage + 1;
        List<Restaurant> favRest = this.restaurantService.getFavouriteRestaurants(sessionObject.getUser().getId());
        Page<Restaurant> favRestPage = pageService.findPaginated
                (PageRequest.of(currentPage - 1, pageSize), favRest);
        model.addAttribute("favRestPage" , favRestPage);
        int totalPages = favRestPage.getTotalPages();

        if(totalPages > 0) {
            List<Integer> pageNumbers =
                    IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());

            model.addAttribute("pageNumbers", pageNumbers);
        }
        if (currentPage > 1) {
            previousPage = currentPage - 1;
        }
        if (currentPage == favRestPage.getTotalPages()) {
            nextPage = currentPage;
        }


        return "myFavourite";
    }

    @GetMapping(value="/addRestaurant")
    public String addRestaurant(Model model) {
        model.addAttribute("restaurant", new Restaurant());

        return "addRestaurant";
    }

    @PostMapping(value="/addRestaurant")
    public String addRestaurantAction(@ModelAttribute Restaurant restaurant, Model model) {
        if(restaurant != null) {
        if (restaurantService.validateRestaurantInput(restaurant)) {
            restaurant.setRestaurantStatus(RestaurantStatus.ACTIVE);
            restaurant.setUserId(sessionObject.getUser().getId());
            this.restaurantService.persistRestaurant(restaurant);
        }
        }else {
            model.addAttribute("alert", "Niepoprawnie wprowadzono dane restauracji.");
            return "addRestaurant";
        }
       return "redirect:/myRestaurants";
    }

    @GetMapping(value="/myRestaurants")
    public String showRestorersRestaurants(Model model,
                                           @RequestParam Optional<Integer> size,
                                           @RequestParam Optional<Integer> page) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(6);
        int previousPage = 1;
        int nextPage = currentPage + 1;

        List<Restaurant> restaurants = this.restaurantService.getRestaurantsByUserId(sessionObject.getUser().getId());
        Page<Restaurant> restaurantPage = pageService.findPaginated
                (PageRequest.of(currentPage - 1, pageSize), restaurants);
        // Page<Restaurant> restaurantPage = restaurantService.findPaginated
        //(PageRequest.of(currentPage - 1, pageSize), restaurantList);
        model.addAttribute("restaurantPage", restaurantPage);

        int totalPages = restaurantPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        if (currentPage > 1) {
            previousPage = currentPage - 1;
        }
        if (currentPage == restaurantPage.getTotalPages()) {
            nextPage = currentPage;
        }
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("previousPage", previousPage);


        return "myRestaurants";
    }

    @GetMapping(value ="/restorerAllRestaurants")
    public String showAllTheRestaurantsToRestorer(Model model,
                                                  @RequestParam Optional<Integer> size,
                                                  @RequestParam Optional<Integer> page) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        int previousPage = 1;
        int nextPage = currentPage + 1;

        List<Restaurant> restaurantList = restaurantService.getActiveRestaurants();
        Page<Restaurant> restaurantPage = pageService.findPaginated
                (PageRequest.of(currentPage - 1, pageSize), restaurantList);
        // Page<Restaurant> restaurantPage = restaurantService.findPaginated
        //(PageRequest.of(currentPage - 1, pageSize), restaurantList);
        model.addAttribute("restaurantPage", restaurantPage);

        int totalPages = restaurantPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        if (currentPage > 1) {
            previousPage = currentPage - 1;
        }
        if (currentPage == restaurantPage.getTotalPages()) {
            nextPage = currentPage;
        }
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("previousPage", previousPage);

        return "restorerAllRestaurants";
    }

    @GetMapping (value = "/restorerMoreInfo/{id}")
    public String showMoreInfoToRestorer(@PathVariable int id, Model model) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        model.addAttribute("restaurant", restaurant);
        return "restorerMoreInfo";
    }
}
