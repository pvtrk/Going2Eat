package pl.camp.it.model;


import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity(name="trestaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private String name;
    private String description;
    private int places;
    private String cuisineType;
    private String city;
    private String adress;
    @Enumerated(EnumType.STRING)
    private RestaurantStatus restaurantStatus;
    @OneToMany(mappedBy = "restaurant")
    private List<Promotion> promotions;
    @OneToMany(mappedBy = "restaurant")
    private List<Blockade> blockades;
    @OneToMany(mappedBy = "restaurant")
    private List<Reservation> reservations;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPlaces() {
        return places;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }


    public RestaurantStatus getRestaurantStatus() {
        return restaurantStatus;
    }

    public void setRestaurantStatus(RestaurantStatus restaurantStatus) {
        this.restaurantStatus = restaurantStatus;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Blockade> getBlockades() {
        return blockades;
    }

    public void setBlockades(List<Blockade> blockades) {
        this.blockades = blockades;
    }

    public static void autoValidateRestaurant(Restaurant restaurant) {
        if(restaurant == null) {
            throw new RestaurantValidationException();
        }
        if(restaurant.getName() == null) {
            throw new RestaurantValidationException();
        }
        if(restaurant.getPlaces() <= 0) {
            throw new RestaurantValidationException();
        }
        if(restaurant.getRestaurantStatus() == null) {
            throw new RestaurantValidationException();
        }
        if(restaurant.getCity() == null) {
            throw new RestaurantValidationException();
        }
        if(restaurant.getId() < 1) {
            throw new RestaurantValidationException();
        }
    }

    public static class RestaurantValidationException extends RuntimeException {

    }
}
