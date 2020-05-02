package pl.camp.it.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.camp.it.model.Restaurant;


import java.util.List;

public interface IRestaurantService {
    void persistRestaurant(Restaurant restaurant);
    List<Restaurant> getAllRestaurants();
    List<Restaurant> getRestaurantsByUserId(int id);
    Restaurant getRestaurantById(int id);
    void addFavouriteRestaurant(int userId, int restaurantId);
    boolean isAlreadyFavourite(int restaurantId);
    List<Restaurant> getFavouriteRestaurants(int userId);
    List<Restaurant> getActiveRestaurants();
    boolean validateRestaurantInput(Restaurant restaurant);
    void deleteRestaurantsPromotionsAndReservations(Restaurant restaurant);
}
