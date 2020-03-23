package pl.camp.it.controllers;

import jdk.nashorn.internal.ir.Block;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.model.Blockade;
import pl.camp.it.model.Reservation;
import pl.camp.it.model.ReservationStatus;
import pl.camp.it.model.Restaurant;
import pl.camp.it.service.IReservationService;
import pl.camp.it.service.IRestaurantService;
import pl.camp.it.service.IUserService;
import pl.camp.it.session.SessionObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    public String submitReservation(@PathVariable int id, @RequestParam String localDateTime,
                                    @RequestParam int guestsNumber, @RequestParam String comments, Model model) {
        sessionObject.setUser(userService.getUserById(1));
        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        int freePlaces = (restaurantService.getRestaurantById(id).getPlaces()) -
                (reservationService.getBookedPlaces(id, LocalDateTime.parse(localDateTime, formatter)));
        if (freePlaces > guestsNumber) {
            this.reservationService.createReservation(this.restaurantService.getRestaurantById(id),
                    guestsNumber, comments, localDateTime);
        }*/
        Restaurant restaurant = this.restaurantService.getRestaurantById(id);
        this.reservationService.doComplexReservationAction(restaurant, guestsNumber, comments, localDateTime);
        return "reservation";
    }

    @GetMapping(value="/myReservations")
    public String showUsersReservations(Model model) {
        sessionObject.setUser(userService.getUserById(1)); // MA POTRZEBY SPRAWDZANIA DZAIALNIA
        List<Reservation> reservationList = reservationService.getReservationsByUserId(sessionObject.getUser().getId());
        model.addAttribute("reservations" , reservationList);
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
    public String restorersReservations(Model model){
        sessionObject.setUser(this.userService.getUserById(2));
        List<Reservation> reservations = this.reservationService.getReservationsForRestorer(sessionObject.getUser());
        model.addAttribute("reservations", reservations);

        return "restorerReservations";
    }

    @GetMapping(value="/decline/{id}")
    public String declineReservation(@PathVariable int id) {
        Reservation reservation = this.reservationService.getReservationById(id);
        reservation.setReservationStatus(ReservationStatus.DECLINED);
        this.reservationService.persistReservation(reservation);
        return "restorerReservations";
    }

    @GetMapping(value="/accept/{id}")
    public String acceptReservation(@PathVariable int id) {
        Reservation reservation = this.reservationService.getReservationById(id);
        reservation.setReservationStatus(ReservationStatus.ACCEPTED);
        this.reservationService.persistReservation(reservation);
        return "restorerReservations";
    }


}
