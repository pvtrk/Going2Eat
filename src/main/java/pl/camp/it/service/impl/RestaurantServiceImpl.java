package pl.camp.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IRestaurantDAO;
import pl.camp.it.model.Restaurant;
import pl.camp.it.service.IRestaurantService;
import pl.camp.it.session.SessionObject;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantServiceImpl implements IRestaurantService {
    @Autowired
    SessionObject sessionObject;
    @Autowired
    IRestaurantDAO restaurantDAO;
    @Override
    public void persistRestaurant(Restaurant restaurant) {
        this.restaurantDAO.persistRestaurant(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return this.restaurantDAO.getAllRestaurants();
    }

    @Override
    public List<Restaurant> getRestaurantsByUserId(int id) {
        return this.restaurantDAO.getRestaurantsByUserId(id);
    }

    @Override
    public Restaurant getRestaurantById(int id) {
       return this.restaurantDAO.getRestaurantById(id);
    }

    @Override
    public void addFavouriteRestaurant(int userId, int restaurantId) {
        this.restaurantDAO.addFavouriteRestaurant(userId, restaurantId);
    }

    @Override
    public boolean checkFavRest(int restaurantId) {
        List<Restaurant> rest = this.restaurantDAO.getFavouriteRestaurants(sessionObject.getUser().getId());
        for (Restaurant restaurant : rest) {
            if (restaurant.getId() == this.restaurantDAO.getRestaurantById(restaurantId).getId()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Restaurant> getFavouriteRestaurants(int userId) {
        return this.restaurantDAO.getFavouriteRestaurants(userId);
    }

    @Override
    public List<Restaurant> getActiveRestaurants() {
        return this.restaurantDAO.getActiveRestaurants();
    }
}
