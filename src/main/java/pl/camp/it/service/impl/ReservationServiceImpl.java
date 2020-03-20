package pl.camp.it.service.impl;

import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IReservationDAO;
import pl.camp.it.dao.IRestaurantDAO;
import pl.camp.it.model.Reservation;
import pl.camp.it.model.Restaurant;
import pl.camp.it.model.User;
import pl.camp.it.service.IReservationService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements IReservationService {
    @Autowired
    IReservationDAO reservationDAO;
    @Autowired
    IRestaurantDAO restaurantDAO;

    @Override
    public void persistReservation(Reservation reservation) {
        this.reservationDAO.persistReservation(reservation);
    }

    @Override
    public Reservation getReservationById(int id) {
        return this.reservationDAO.getReservationById(id);
    }

    @Override
    public List<Reservation> getReservationsByRestaurantId(int id) {
        return this.reservationDAO.getReservationsByRestaurantId(id);
    }

    @Override
    public int getBookedPlaces(int id, LocalDateTime time) {
        int guestNumber = 0;
        Restaurant restaurant = this.restaurantDAO.getRestaurantById(id);
        List<Reservation> reservationsList = this.reservationDAO.getReservationsByRestaurantId(id);
        for(Reservation reserv : reservationsList) {
            if(reserv.getStartTime().isAfter(time.plusHours(2)) || reserv.getEndTime().isBefore(time)) {
                continue;
            }
            guestNumber = guestNumber + reserv.getGuestsQuantity();
        }

        return guestNumber;
    }

    @Override
    public List<Reservation> getReservationsByUserId(int id) {
        return this.reservationDAO.getReservationsByUserId(id);
    }

    @Override
    public List<Reservation> getActiveReservationsForUser(int id) {
        return this.reservationDAO.getActiveReservationsForUser(id);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return this.reservationDAO.getAllReservations();
    }

    @Override
    public List<Reservation> getReservationsForRestorer(User restorer) {
        List<Restaurant> restaurants = this.restaurantDAO.getRestaurantsByUserId(restorer.getId());
        List<Reservation> result = new ArrayList<>();
        for(Restaurant restaurant : restaurants) {
            result.addAll(this.reservationDAO.getReservationsByRestaurantId(restaurant.getId()));
        }
        return result;
    }
}
