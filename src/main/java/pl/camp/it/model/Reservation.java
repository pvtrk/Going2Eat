package pl.camp.it.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name="treservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private int restaurantId;
    private int guestsQuantity;
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

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return this.startTime.plusHours(2);
    }

    public void setEndTime(LocalDateTime startTime) {
        this.endTime = startTime.plusHours(2);
    }
}
