package pl.camp.it.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.camp.it.dao.IReservationDAO;
import pl.camp.it.model.Reservation;
import pl.camp.it.model.Restaurant;

import java.time.LocalDateTime;
import java.util.List;

public interface IReservationService {
    void persistReservation(Reservation reservation);
    List<Reservation> getReservationsByRestaurantId(int id);
    int getBookedPlaces(int id, LocalDateTime time);


}
