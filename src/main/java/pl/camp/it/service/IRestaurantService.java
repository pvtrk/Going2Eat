package pl.camp.it.service;

import pl.camp.it.model.Restaurant;

import java.util.List;

public interface IRestaurantService {
    void persistRestaurant(Restaurant restaurant);
    List<Restaurant> getAllRestaurants();
    List<Restaurant> getRestaurantsByUserId(int id);
    Restaurant getRestaurantById(int id);
    void addFavouriteRestaurant(int userId, int restaurantId);
    boolean checkFavRest(int restaurantId);
    List<Restaurant> getFavouriteRestaurants(int userId);
    List<Restaurant> getActiveRestaurants();
}
