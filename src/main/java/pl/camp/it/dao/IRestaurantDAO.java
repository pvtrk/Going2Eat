package pl.camp.it.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.camp.it.model.Restaurant;

import java.util.List;

public interface IRestaurantDAO {
    void persistRestaurant(Restaurant restaurant);
    List<Restaurant> getAllRestaurants();
    List<Restaurant> getRestaurantsByUserId(int id);
    Restaurant getRestaurantById(int id);
    void addFavouriteRestaurant(int userId, int restaurantId);
    List<Restaurant> getFavouriteRestaurants(int userId);
    List<Restaurant> getActiveRestaurants();
    List<Restaurant> getAllOtherRestaurantsForRestorer(int userId);

}
