package pl.camp.it.service;

import pl.camp.it.model.Restaurant;

import java.util.List;

public interface IRestaurantService {
    void persistRestaurant(Restaurant restaurant);
    List<Restaurant> getAllRestaurants();
    Restaurant getRestaurantById(int id);
}
