package pl.camp.it.model;



import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity(name="tpromotion")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "restaurantId")
    private Restaurant restaurant;
    private String description;
    private int price;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private PromotionStatus status;
    private boolean distinction;

    public int getId() {
        return id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public PromotionStatus getStatus() {
        return status;
    }

    public void setStatus(PromotionStatus status) {
        this.status = status;
    }

    public boolean isDistinction() {
        return distinction;
    }

    public void setDistinction(boolean distinction) {
        this.distinction = distinction;
    }

    public String getStartTimeDisplayValue() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.startDate.format(formatter);
    }

    public static void autoValidatePromotion(Promotion promotion) {
        if(promotion == null) {
            throw new PromotionValidationException();
        }
        if(promotion.getRestaurant() == null) {
            throw new PromotionValidationException();
        }
        if(promotion.getStartDate() == null) {
            throw new PromotionValidationException();
        }
        if(promotion.getDescription() == null) {
            throw new PromotionValidationException();
        }
        if(promotion.getStatus() == null) {
            throw new PromotionValidationException();
        }
        if(promotion.getPrice() < 1) {
            throw new PromotionValidationException();
        }
        if(promotion.getId() < 1) {
            throw new PromotionValidationException();
        }

    }

    public static class PromotionValidationException extends RuntimeException{

    }
}
