package pl.camp.it.service.impl;

import org.apache.tomcat.jni.Local;
import org.hibernate.Session;

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
import java.util.Comparator;
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
        result.sort(new Comparator<Reservation>() {
            @Override
            public int compare(Reservation r1, Reservation r2) {
                if((r1.getReservationStatus().getNumberValue() - r2.getReservationStatus().getNumberValue()) != 0) {

                    return r1.getReservationStatus().getNumberValue() - r2.getReservationStatus().getNumberValue();

                } return r1.getRestaurantName().compareTo(r2.getRestaurantName());
            }
        });
        return result;
    }

    @Override
    public boolean doComplexReservationAction(Restaurant restaurant, int guestsNumber,
                                           String comments, String reservationStartTime) {
        LocalDateTime startTime = parseStringToDate(reservationStartTime);

        int freePlaces = (restaurant.getPlaces()) -
                (getBookedPlaces(restaurant.getId(), startTime));

        if (freePlaces > guestsNumber) {

            createReservation(restaurant,
                    guestsNumber, comments, startTime);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isBlocked(int restaurantId, String startTime) {
        LocalDateTime start = parseStringToDate(startTime);
        LocalDateTime end = start.plusHours(2);

        List<Blockade> blockList = this.blockadeService.getActiveBlockadesForRestaurant(restaurantId);

        for(Blockade blockade : blockList) {
            if(end.isBefore(blockade.getStartDate()) ||
                    ((blockade.getEndDate() != null) && start.isAfter(blockade.getEndDate()))) {
                continue;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Reservation> getWaitingReservationsForRestaurant(int id) {
        return reservationDAO.getWaitingReservationsForRestaurant(id);
    }

    @Override
    public List<Reservation> getAcceptedReservationsForRestaurant(int id) {
        return reservationDAO.getAcceptedReservationsForRestaurant(id);
    }

    @Override
    public List<Reservation> getDeclinedReservationsForRestaurant(int id) {
        return reservationDAO.getDeclinedReservationsForRestaurant(id);
    }

    @Override
    public List<Reservation> getCanceledReservationsForRestaurant(int id) {
        return reservationDAO.getCanceledReservationsForRestaurant(id);
    }

    private LocalDateTime parseStringToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateString, formatter);
    }

    private int getBookedPlaces(int id, LocalDateTime time) {
        int bookedPlaces = 0;
        List<Reservation> reservationsList = this.reservationDAO.getReservationsByRestaurantId(id);

        for(Reservation reserv : reservationsList) {
            if(reserv.getStartTime().isAfter(time.plusHours(2)) || reserv.getEndTime().isBefore(time)) {
                continue;
            }
            bookedPlaces += reserv.getGuestsQuantity();
        }

        return bookedPlaces;
    }

    private void createReservation(Restaurant restaurant, int guestNumber,
                                   String comments, LocalDateTime startTime) {
        Reservation reservation = new Reservation();
        reservation.setUserId(sessionObject.getUser().getId());
        reservation.setRestaurantId(restaurant.getId());
        reservation.setRestaurantName(restaurant.getName());
        reservation.setGuestsQuantity(guestNumber);
        reservation.setReservationStatus(ReservationStatus.WAITING);
        reservation.setComments(comments);
        reservation.setStartTime(startTime);
        reservation.setEndTime(startTime.plusHours(2));
        this.reservationService.persistReservation(reservation);
    }
}
