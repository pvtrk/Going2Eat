package pl.camp.it.service.impl;

import org.apache.tomcat.jni.Local;
import org.hibernate.Session;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IReservationDAO;
import pl.camp.it.dao.IRestaurantDAO;
import pl.camp.it.model.*;
import pl.camp.it.service.IBlockadeService;
import pl.camp.it.service.IReservationService;
import pl.camp.it.session.SessionObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements IReservationService {
    @Autowired
    IReservationDAO reservationDAO;
    @Autowired
    IRestaurantDAO restaurantDAO;
    @Autowired
    IReservationService reservationService;
    @Autowired
    IBlockadeService blockadeService;
    @Autowired
    SessionObject sessionObject;

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

    @Override
    public boolean isBlocked(int restaurantId, String reservationStartTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime start = LocalDateTime.parse(reservationStartTime, formatter);
        LocalDateTime end = start.plusHours(2);
        List<Blockade> blockList = this.blockadeService.getBlockadesByRestaurantId(restaurantId);
        for(Blockade blockade : blockList) {
            if(end.isBefore(blockade.getStartDate()) ||
                    ((blockade.getEndDate() != null) && start.isAfter(blockade.getEndDate()))) {
                continue;
            } else if (blockade.getEndDate() == null && blockade.getStartDate().isBefore(end)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void createReservation(Restaurant restaurant, int guestNumber, String comments, String reservationStartTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Reservation reservation = new Reservation();
        reservation.setUserId(sessionObject.getUser().getId());
        reservation.setRestaurantId(restaurant.getId());
        reservation.setRestaurantName(restaurant.getName());
        reservation.setGuestsQuantity(guestNumber);
        reservation.setReservationStatus(ReservationStatus.WAITING);
        reservation.setComments(comments);
        reservation.setStartTime(LocalDateTime.parse(reservationStartTime, formatter));
        reservation.setEndTime(LocalDateTime.parse(reservationStartTime, formatter).plusHours(2));
        this.reservationService.persistReservation(reservation);
    }

    @Override
    public void doComplexReservationAction(Restaurant restaurant, int guestsNumber,
                                           String comments, String reservationStartTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        int freePlaces = (restaurant.getPlaces()) -
                (getBookedPlaces(restaurant.getId(), LocalDateTime.parse(reservationStartTime, formatter)));
        if ((!isBlocked(restaurant.getId(), reservationStartTime)) &&freePlaces > guestsNumber) {
            createReservation(restaurant,
                    guestsNumber, comments, reservationStartTime);
        }
    }
}
