package pl.camp.it.dao;

import pl.camp.it.model.Reservation;

import java.util.List;

public interface IReservationDAO {
    void persistReservation (Reservation reservation);
    Reservation getReservationById(int id);
    List<Reservation> getAllReservations();
    List<Reservation> getReservationsByUserId(int id);
    List<Reservation> getReservationsByRestaurantId(int id);
    List<Reservation> getActiveReservationsForUser(int id);
    List<Reservation> getWaitingReservationsForRestaurant(int id);
    List<Reservation> getAcceptedReservationsForRestaurant(int id);
    List<Reservation> getDeclinedReservationsForRestaurant(int id);


}
