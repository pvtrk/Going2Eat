package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.camp.it.model.*;
import pl.camp.it.service.IReservationService;
import pl.camp.it.service.IRestaurantService;
import pl.camp.it.service.IUserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    IRestaurantService restaurantService;
    @Autowired
    IReservationService reservationService;
    @Autowired
    IUserService userService;
    @GetMapping(value="/adminMenu")
    public String showAdminMenu() {
        return "adminMenu";
    }

    @GetMapping(value="/adminRestaurants")
    public String showAllRestaurants(Model model) {
        List<Restaurant> restaurantList = this.restaurantService.getAllRestaurants();
        model.addAttribute("restaurants" , restaurantList);
        return "adminRestaurants";
    }

    @GetMapping(value="/block/{id}")
    public String blockRestaurant(@PathVariable int id) {
       Restaurant restaurant = this.restaurantService.getRestaurantById(id);
       restaurant.setRestaurantStatus(RestaurantStatus.OFF);
       this.restaurantService.persistRestaurant(restaurant);
       return "redirect:/adminRestaurants";
    }

    @GetMapping(value="/unblock/{id}")
    public String unblockRestaurant(@PathVariable int id) {
        Restaurant restaurant = this.restaurantService.getRestaurantById(id);
        restaurant.setRestaurantStatus(RestaurantStatus.ACTIVE);
        this.restaurantService.persistRestaurant(restaurant);
        return "redirect:/adminRestaurants";
    }

    @GetMapping(value="/adminReservations")
    public String showAllReservations(Model model) {
        List<Reservation> reservations = this.reservationService.getAllReservations();
        model.addAttribute("reservations" , reservations);
        return "adminReservations";
    }

    @GetMapping(value="/showUser/{id}")
    public String showInformationsAboutUser(@PathVariable int id, Model model) {
        User user = this.userService.getUserById(id);
        List<Restaurant> restaurant = this.restaurantService.getRestaurantsByUserId(id);
        model.addAttribute("user", user);
        model.addAttribute("restaurants", restaurant);
        return "showUser";
    }

    @GetMapping(value="/users")
    public String showAllUsers(Model model) {
        List<User> users = this.userService.getAllUsers();
        List<User> result = new ArrayList<>();
        for(User user : users) {
            if(user.getRole().equals(UserRole.ADMIN)) {
                continue;
            } result.add(user);
        }
        model.addAttribute("users", result);

        return "users";
    }

    @GetMapping(value="/userRestaurants/{id}")
    public String showUsersRestaurants(@PathVariable int id, Model model) {
        List<Restaurant> restaurants = this.restaurantService.getRestaurantsByUserId(id);
        model.addAttribute("restaurants", restaurants);

        return "userRestaurants";
    }
}
