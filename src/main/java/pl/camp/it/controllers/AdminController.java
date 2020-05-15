package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.model.*;
import pl.camp.it.service.IReservationService;
import pl.camp.it.service.IRestaurantService;
import pl.camp.it.service.IUserService;
import pl.camp.it.service.impl.PageService;
import pl.camp.it.session.SessionObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AdminController {
    @Autowired
    PageService pageService;
    @Autowired
    SessionObject sessionObject;
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
    public String showAllRestaurants(Model model,
                                     @RequestParam Optional<Integer> size,
                                     @RequestParam Optional<Integer> page) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        int nextPage = currentPage + 1;
        int previousPage = 1;

        List<Restaurant> restaurantList = this.restaurantService.getAllRestaurants();
        Page<Restaurant> restaurantPage = pageService.findPaginated
                (PageRequest.of(currentPage - 1, pageSize), restaurantList );
        model.addAttribute("restaurantPage" , restaurantPage);

        int totalPages = restaurantPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers =
                    IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers" , pageNumbers);
        }
        if (currentPage > 1) {
            previousPage = currentPage - 1;
        }
        if (currentPage == restaurantPage.getTotalPages()) {
            nextPage = currentPage;
        }

        return "adminRestaurants";
    }

    @GetMapping(value="/blockRestaurant/{id}")
    public String blockRestaurant(@PathVariable int id) {
       Restaurant restaurant = this.restaurantService.getRestaurantById(id);
       restaurant.setRestaurantStatus(RestaurantStatus.OFF);
       this.restaurantService.persistRestaurant(restaurant);
       return "redirect:/adminRestaurants";
    }

    @GetMapping(value="/unblockRestaurant/{id}")
    public String unblockRestaurant(@PathVariable int id) {
        Restaurant restaurant = this.restaurantService.getRestaurantById(id);
        restaurant.setRestaurantStatus(RestaurantStatus.ACTIVE);
        this.restaurantService.persistRestaurant(restaurant);
        return "redirect:/adminRestaurants";
    }

    @GetMapping(value="/adminReservations")
    public String showAllReservations(Model model,
                                      @RequestParam Optional<Integer> size,
                                      @RequestParam Optional<Integer> page) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        int nextPage = currentPage + 1;
        int previousPage = 1;

        List<Reservation> reservationList = this.reservationService.getAllReservations();
        Page<Reservation> reservationsPage = pageService.findPaginated
                (PageRequest.of(currentPage - 1, pageSize), reservationList );
        model.addAttribute("reservationsPage" , reservationsPage);

        int totalPages = reservationsPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers =
                    IntStream.rangeClosed(1, totalPages)
                            .boxed()
                            .collect(Collectors.toList());

            model.addAttribute("pageNumbers" , pageNumbers);
        }
        if (currentPage > 1) {
            previousPage = currentPage - 1;
        }
        if (currentPage == reservationsPage.getTotalPages()) {
            nextPage = currentPage;
        }
        return "adminReservations";
    }

    @GetMapping(value="/showUser/{id}")
    public String showInformationsAboutUser(@PathVariable int id, Model model) {
        User user = this.userService.getUserById(id);
        List<Restaurant> restaurant = new ArrayList<>();
        if (user != null) {
            restaurant = this.restaurantService.getRestaurantsByUserId(id);
        }
        model.addAttribute("user", user);
        model.addAttribute("restaurants", restaurant);
        return "showUser";
    }

    @GetMapping(value="/users")
    public String showAllUsers(Model model,
                               @RequestParam Optional<Integer> size,
                               @RequestParam Optional<Integer> page) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        int nextPage = currentPage + 1;
        int previousPage = 1;

        List<User> users = this.userService.getAllUsers();
        List<User> result = new ArrayList<>();
        for(User user : users) {
            if((user != null && user.getRole() != null)
                    && user.getRole().equals(UserRole.ADMIN)) {
                continue;
            } result.add(user);
        }
        Page<User> usersPage =
                pageService.findPaginated(PageRequest.of(currentPage - 1, pageSize), result);
        model.addAttribute("usersPage", usersPage);

        int totalPages = usersPage.getTotalPages();

        if(totalPages > 0) {
            List<Integer> pageNumbers =
                    IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        if(currentPage > 1) {
            previousPage = currentPage - 1;
        }
        if(currentPage == usersPage.getTotalPages()) {
            nextPage = currentPage;
        }

        return "users";
    }

    @GetMapping(value="/userRestaurants")
    public String showUsersRestaurants(@RequestParam int id, Model model,
                                       @RequestParam Optional<Integer> size,
                                       @RequestParam Optional<Integer> page) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        int nextPage = currentPage + 1;
        int previousPage = 1;

        List<Restaurant> restaurants = this.restaurantService.getRestaurantsByUserId(id);

        Page<Restaurant> restaurantPage=
                pageService.findPaginated(PageRequest.of(currentPage - 1, pageSize), restaurants);
        model.addAttribute("restaurantPage", restaurantPage);

        int totalPages = restaurantPage.getTotalPages();

        if(totalPages > 0) {
            List<Integer> pageNumbers =
                    IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        if(currentPage > 1) {
            previousPage = currentPage - 1;
        }
        if(currentPage == restaurantPage.getTotalPages()) {
            nextPage = currentPage;
        }

        model.addAttribute("id", id);
        return "userRestaurants";
    }
}
