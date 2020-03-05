package pl.camp.it.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.model.Reservation;
import pl.camp.it.service.IReservationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class CommonController {
    @Autowired
    IReservationService reservationService;

    @GetMapping(value = "/main")
    public String showMainPage() {
        return "main";
    }

    @GetMapping(value = "/reservation")
    public String testReservation() {
        return "reservation";
    }
    /*@GetMapping(value = "/makeReservation")
    public String testMaking(@RequestParam String localDateTime, @RequestParam int guestsNumber) {
        System.out.println(localDateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime reservationTime = LocalDateTime.parse(localDateTime, formatter);
        Reservation reservation = new Reservation();
        reservation.setGuestsQuantity(guestsNumber);
        reservation.setRestaurantId(1);
        reservation.setUserId(2);
        reservation.setStartTime(reservationTime);
        reservation.setEndTime(reservationTime);
        this.reservationService.persistReservation(reservation);
        System.out.println(this.reservationService.getBookedPlaces(1, reservationTime));

        return "main";
    } */
}
