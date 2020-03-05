package pl.camp.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IRestaurantDAO;
import pl.camp.it.model.Restaurant;
import pl.camp.it.service.IRestaurantService;

import java.util.List;

@Service
public class RestaurantServiceImpl implements IRestaurantService {
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
    public Restaurant getRestaurantById(int id) {
       return this.restaurantDAO.getRestaurantById(id);
    }
}
