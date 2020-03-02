package pl.camp.it.dao;

import pl.camp.it.model.Reservation;

import java.util.List;

public interface IReservationDAO {
    void persistReservation (Reservation reservation);
    List<Reservation> getReservationsByUserId(int id);
    List<Reservation> getReservationsByRestaurantId(int id);

}
