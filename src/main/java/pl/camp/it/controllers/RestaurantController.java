package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.camp.it.model.Restaurant;
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
    @GetMapping(value="/moreinfo/{id}")
    public String moreInfoAboutRestaurant(@PathVariable int id, Model model) {

        Restaurant restaurant = restaurantService.getRestaurantById(id);
        model.addAttribute("restaurant", restaurant);
        return "moreinfo";
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
}
