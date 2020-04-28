package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.model.*;
import pl.camp.it.service.IReservationService;
import pl.camp.it.service.IRestaurantService;
import pl.camp.it.service.impl.PageService;
import pl.camp.it.session.SessionObject;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ReservationController {
    @Autowired
    PageService pageService;
    @Autowired
    IReservationService reservationService;
    @Autowired
    IRestaurantService restaurantService;
    @Autowired
    SessionObject sessionObject;

    @GetMapping(value="/makeReservation/{id}")
    public String makeReservation(@PathVariable int id) {

        return "reservation";
    }

    @PostMapping(value="/makeReservation/{id}")
    public String submitReservation(@PathVariable int id, @RequestParam String localDateTime,
                                    @RequestParam int guestsNumber, @RequestParam String comments, Model model) {

        Restaurant restaurant = this.restaurantService.getRestaurantById(id);
        if(!this.reservationService.isBlocked(restaurant.getId(), localDateTime)) {
            boolean isDone = this.reservationService.doComplexReservationAction(restaurant, guestsNumber, comments, localDateTime);
            if (isDone) {
                model.addAttribute("message", "Gratulacje, udało Ci się dokonać rezerwacji!");
            } else {
                model.addAttribute("message", "Niestety nie udało się zrobić rezerwacji");
            }
        } else {
            model.addAttribute("message", "Restauracja w tym okresie nie przyjmuje żadnych rezerwacji");
        }
        return "reservation";
    }

    @GetMapping(value="/myReservations")
    public String showUsersReservations(Model model,
                                        @RequestParam Optional<Integer> size,
                                        @RequestParam Optional<Integer> page) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        int nextPage = currentPage + 1;
        int previousPage = 1;

        List<Reservation> reservationList = reservationService.getReservationsByUserId(sessionObject.getUser().getId());
        Page<Reservation> reservationsPage = pageService.findPaginated
                (PageRequest.of(currentPage - 1, pageSize), reservationList );
        model.addAttribute("reservationsPage" , reservationsPage);

        int totalPages = reservationsPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers =
                    IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers" , pageNumbers);
        }
        if (currentPage > 1) {
            previousPage = currentPage - 1;
        }
        if (currentPage == reservationsPage.getTotalPages()) {
            nextPage = currentPage;
        }
        return "myReservations";
    }

    @GetMapping(value="/cancel/{id}")
    public String cancelReservation(@PathVariable int id) {
        Reservation reservation = this.reservationService.getReservationById(id);
        reservation.setReservationStatus(ReservationStatus.CANCELED);
        this.reservationService.persistReservation(reservation);
        return "redirect:/myReservations";
    }

    @GetMapping(value="/restorerReservations")
    public String restorersReservations(Model model,
                                        @RequestParam Optional<Integer> size,
                                        @RequestParam Optional<Integer> page,
                                        @RequestParam Optional<Integer> type,
                                        @RequestParam Optional<Integer> id){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        int nextPage = currentPage + 1;
        int previousPage = 1;
        int sortType = type.orElse(0);
        int restaurantId = id.orElse(0);

        model.addAttribute("type", sortType);
        model.addAttribute("id", restaurantId);

        List<Reservation> reservations;
        if(sortType == 1 && restaurantId != 0) {
            reservations = reservationService.getWaitingReservationsForRestaurant(restaurantId);
        } else if(sortType == 2 && restaurantId != 0) {
            reservations = reservationService.getAcceptedReservationsForRestaurant(restaurantId);
        } else if(sortType == 3 && restaurantId != 0)  {
            reservations = reservationService.getDeclinedReservationsForRestaurant(restaurantId);
        } else {
           reservations = reservationService.getReservationsForRestorer(sessionObject.getUser());
        }

        Page<Reservation> reservationsPage = pageService.findPaginated
                (PageRequest.of(currentPage - 1, pageSize), reservations );
        model.addAttribute("reservationsPage" , reservationsPage);

        List<Restaurant> restaurants = restaurantService.getRestaurantsByUserId(sessionObject.getUser().getId());
        model.addAttribute("restaurants", restaurants);
        int totalPages = reservationsPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers =
                    IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers" , pageNumbers);
        }
        if (currentPage > 1) {
            previousPage = currentPage - 1;
        }
        if (currentPage == reservationsPage.getTotalPages()) {
            nextPage = currentPage;
        }
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("previousPage", previousPage);
        return "restorerReservations";
    }


    @GetMapping(value="/decline/{id}")
    public String declineReservation(@PathVariable int id) {
        Reservation reservation = this.reservationService.getReservationById(id);
        reservation.setReservationStatus(ReservationStatus.DECLINED);
        this.reservationService.persistReservation(reservation);
        return "redirect:/restorerReservations";
    }

    @GetMapping(value="/accept/{id}")
    public String acceptReservation(@PathVariable int id) {
        Reservation reservation = this.reservationService.getReservationById(id);
        reservation.setReservationStatus(ReservationStatus.ACCEPTED);
        this.reservationService.persistReservation(reservation);
        return "redirect:/restorerReservations";
    }


}
