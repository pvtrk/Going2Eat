package pl.camp.it.dao;

import pl.camp.it.model.Restaurant;

import java.util.List;

public interface IRestaurantDAO {
    void persistRestaurant(Restaurant restaurant);
    List<Restaurant> getAllRestaurants();
    List<Restaurant> getRestaurantByUserId(int id);
    Restaurant getRestaurantById(int id);
}
