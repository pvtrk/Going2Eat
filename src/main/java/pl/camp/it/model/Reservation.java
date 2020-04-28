package pl.camp.it.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity(name="treservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private int restaurantId;
    private String restaurantName;
    private int guestsQuantity;
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;
    private String comments;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public int getId() {
        return id;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getGuestsQuantity() {
        return guestsQuantity;
    }

    public void setGuestsQuantity(int guestsQuantity) {
        this.guestsQuantity = guestsQuantity;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getStartTimeDisplayValue() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return this.startTime.format(formatter);
    }
    public String getEndTimeDisplayValue() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return this.startTime.plusHours(2).format(formatter);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {

        return this.startTime.plusHours(2);
    }

    public void setEndTime(LocalDateTime startTime) {
        this.endTime = startTime.plusHours(2);
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public static void autoValidateReservation(Reservation reservation) {
        if(reservation == null) {
            throw new RestaurantValidationException();
        }
        if(reservation.getRestaurantName() == null) {
            throw new RestaurantValidationException();
        }
        if(reservation.getReservationStatus() == null) {
            throw new RestaurantValidationException();
        }
        if(reservation.getGuestsQuantity() < 1 ) {
            throw new RestaurantValidationException();
        }
        if(reservation.getStartTime() == null) {
            throw new RestaurantValidationException();
        }
        if(reservation.getRestaurantId() < 1) {
            throw new RestaurantValidationException();
        }
        if(reservation.getUserId() <1) {
            throw new RestaurantValidationException();
        }
    }

    public static class RestaurantValidationException extends RuntimeException {

    }
}
