package pl.camp.it.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="tblockade")
public class Blockade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    @ManyToOne
    @JoinColumn(name = "restaurantId")
    private Restaurant restaurant;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    boolean active;

    public int getId() {
        return id;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public static void autoValidateBlockade(Blockade blockade) {
        if (blockade == null) {
            throw new BlockadeValidationException();
        }
        if(blockade.getRestaurant() == null) {
            throw new BlockadeValidationException();
        }
        if(blockade.getStartDate() == null) {
            throw new BlockadeValidationException();
        }
        if(blockade.getUserId() < 1) {
            throw new BlockadeValidationException();
        }

    }

    public static class BlockadeValidationException extends RuntimeException{

    }
}
