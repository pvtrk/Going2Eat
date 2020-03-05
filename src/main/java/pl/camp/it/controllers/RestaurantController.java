package pl.camp.it.controllers;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.camp.it.model.Restaurant;
import pl.camp.it.service.IRestaurantService;
import pl.camp.it.session.SessionObject;

import java.util.List;

@Controller
public class RestaurantController {
    @Autowired
    IRestaurantService restaurantService;
    @Autowired
    SessionObject sessionObject;

    @GetMapping(value ="/restaurants")
    public String showAllTheRestaurants(Model model) {
        List<Restaurant> restaurantList = restaurantService.getAllRestaurants();

         model.addAttribute("restaurants", restaurantList);
         return "restaurants";
    }
}
