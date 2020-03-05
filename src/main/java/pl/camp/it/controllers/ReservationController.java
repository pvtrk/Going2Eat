package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.model.Reservation;
import pl.camp.it.service.IReservationService;
import pl.camp.it.service.IRestaurantService;
import pl.camp.it.service.IUserService;
import pl.camp.it.session.SessionObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ReservationController {
    @Autowired
    IReservationService reservationService;
    @Autowired
    IRestaurantService restaurantService;
    @Autowired
    IUserService userService;
    @Autowired
    SessionObject sessionObject;

    @GetMapping(value="/makeReservation/{id}")
    public String makeReservation(@PathVariable int id, Model model) {

        return "reservation";
    }

    @PostMapping(value="/makeReservation/{id}")
    public String submitReservation(@PathVariable int id, @RequestParam String localDateTime, @RequestParam int guestsNumber, Model model) {
        sessionObject.setUser(userService.getUserById(2));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        int freePlaces = restaurantService.getRestaurantById(id).getPlaces() - reservationService.getBookedPlaces(id, LocalDateTime.parse(localDateTime, formatter));
        if (freePlaces > guestsNumber) {
            Reservation reservation = new Reservation();
            reservation.setUserId(sessionObject.getUser().getId());
            reservation.setRestaurantId(id);
            reservation.setGuestsQuantity(guestsNumber);
            reservation.setStartTime(LocalDateTime.parse(localDateTime, formatter));
            reservation.setEndTime(LocalDateTime.parse(localDateTime, formatter));
            reservationService.persistReservation(reservation);
        }
        return "reservation";
    }
}