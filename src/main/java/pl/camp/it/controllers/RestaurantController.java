package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.model.Restaurant;
import pl.camp.it.model.RestaurantStatus;
import pl.camp.it.service.IRestaurantService;
import pl.camp.it.service.IUserService;
import pl.camp.it.session.SessionObject;

import java.util.List;

@Controller
public class RestaurantController {
    @Autowired
    IRestaurantService restaurantService;
    @Autowired
    IUserService userService;
    @Autowired
    SessionObject sessionObject;

    @GetMapping(value ="/restaurants")
    public String showAllTheRestaurants(Model model) {
        List<Restaurant> restaurantList = restaurantService.getActiveRestaurants();

         model.addAttribute("restaurants", restaurantList);
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
        sessionObject.setUser(userService.getUserById(2));
        if(this.restaurantService.checkFavRest(id)) {
            this.restaurantService.addFavouriteRestaurant(sessionObject.getUser().getId(), id);
        }
        return "restaurants";
    }

    @GetMapping(value="/myFavourite")
    public String showFavourites(Model model) {
        sessionObject.setUser(userService.getUserById(2));
        List<Restaurant> favRest = this.restaurantService.getFavouriteRestaurants(sessionObject.getUser().getId());
        model.addAttribute("favourites" , favRest);
        return "myFavourite";
    }

    @GetMapping(value="/addRestaurant")
    public String addRestaurant(Model model) {
        model.addAttribute("restaurant", new Restaurant());

        return "addRestaurant";
    }

    @PostMapping(value="/addRestaurant")
    public String addRestaurantAction(@ModelAttribute Restaurant restaurant) {
       sessionObject.setUser(userService.getUserById(1)); //PRZED WPROWADZENIEM SYSTEMU LOGOWANIA
       restaurant.setRestaurantStatus(RestaurantStatus.ACTIVE);
       restaurant.setUserId(sessionObject.getUser().getId());
       this.restaurantService.persistRestaurant(restaurant);

       return "redirect:/myRestaurants";
    }

    @GetMapping(value="/myRestaurants")
    public String showRestorersRestaurants(Model model) {
        sessionObject.setUser(userService.getUserById(2));
        List<Restaurant> restaurants = this.restaurantService.getRestaurantsByUserId(sessionObject.getUser().getId());

        model.addAttribute("restaurants" , restaurants);

        return "myRestaurants";
    }

    @GetMapping(value ="/restorerAllRestaurants")
    public String showAllTheRestaurantsToRestorer(Model model) {
        List<Restaurant> restaurantList = restaurantService.getActiveRestaurants();

        model.addAttribute("restaurants", restaurantList);
        return "restorerAllRestaurants";
    }
}
