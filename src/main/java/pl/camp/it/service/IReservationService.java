package pl.camp.it.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.camp.it.dao.IReservationDAO;
import pl.camp.it.model.Reservation;
import pl.camp.it.model.Restaurant;
import pl.camp.it.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface IReservationService {
    void persistReservation(Reservation reservation);
    Reservation getReservationById(int id);
    List<Reservation> getReservationsByRestaurantId(int id);
    List<Reservation> getReservationsByUserId(int id);
    List<Reservation> getActiveReservationsForUser(int id);
    List<Reservation> getAllReservations();
    List<Reservation> getReservationsForRestorer(User restorer);
    boolean doComplexReservationAction(Restaurant restaurant, int guestNumber,
                                    String comments, String reservationStartTime);
    boolean isBlocked(int restaurantId, String startTime);

    List<Reservation> getWaitingReservationsForRestaurant(int id);
    List<Reservation> getAcceptedReservationsForRestaurant(int id);
    List<Reservation> getDeclinedReservationsForRestaurant(int id);
    List<Reservation> getCanceledReservationsForRestaurant(int id);
}
