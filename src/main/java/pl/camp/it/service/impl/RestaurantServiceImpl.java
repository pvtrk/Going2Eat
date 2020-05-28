package pl.camp.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import pl.camp.it.dao.IRestaurantDAO;
import pl.camp.it.model.*;
import pl.camp.it.service.IPromotionService;
import pl.camp.it.service.IReservationService;
import pl.camp.it.service.IRestaurantService;
import pl.camp.it.session.SessionObject;
import pl.camp.it.utils.RegexChecker;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RestaurantServiceImpl implements IRestaurantService {
    @Autowired
    RegexChecker regexChecker;
    @Autowired
    SessionObject sessionObject;
    @Autowired
    IRestaurantDAO restaurantDAO;
    @Autowired
    IPromotionService promotionService;
    @Autowired
    IReservationService reservationService;
    @Override
    public void persistRestaurant(Restaurant restaurant) {
        restaurantDAO.persistRestaurant(restaurant);
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
    public boolean isAlreadyFavourite(int restaurantId) {
        List<Restaurant> rest = this.restaurantDAO.getFavouriteRestaurants(sessionObject.getUser().getId());
        for (Restaurant restaurant : rest) {
            if (restaurant.getId() == this.restaurantDAO.getRestaurantById(restaurantId).getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Restaurant> getFavouriteRestaurants(int userId) {
        return this.restaurantDAO.getFavouriteRestaurants(userId);
    }

    @Override
    public List<Restaurant> getActiveRestaurants() {
        return this.restaurantDAO.getActiveRestaurants();
    }

    @Override
    public boolean validateRestaurantInput(Restaurant restaurant) {
        if((restaurant.getPlaces() != 0) &&
                (restaurant.getPlaces() <= 999 && restaurant.getPlaces() > 0)) {
            if((restaurant.getName() != null) &&
                    regexChecker.checkInput(restaurant.getName(), regexChecker.getRestaurantNameRegex())) {
                if((restaurant.getAdress() != null) &&
                        regexChecker.checkInput(restaurant.getAdress(), regexChecker.getRestaurantAdressRegex())) {
                    if((restaurant.getCity() != null) &&
                            regexChecker.checkInput(restaurant.getCity(), regexChecker.getRestaurantAdressRegex())) {
                        if((restaurant.getCuisineType() != null) &&
                                regexChecker.checkInput(restaurant.getCuisineType(), regexChecker.getRestaurantCuisineRegex())) {
                            if((restaurant.getDescription() != null) && regexChecker.
                                    checkInput(restaurant.getDescription(), regexChecker.getDescriptionRegex())) {
                                return true;
                            }
                        }
                    }
                }
            }
        } return false;
    }

    @Override
    public void deleteRestaurantsPromotionsAndReservations(Restaurant restaurant) {
        if(restaurant != null) {
            List<Promotion> promotions = promotionService.getPromotionsByRestaurantId(restaurant.getId());
            for(Promotion promotion : promotions) {
                promotion.setStatus(PromotionStatus.OFF);
                promotionService.persistPromotion(promotion);
            }
            List<Reservation> reservations = reservationService.getReservationsByRestaurantId(restaurant.getId());
            for(Reservation reservation : reservations) {
                if(reservation.getStartTime().isAfter(LocalDateTime.now())) {
                    reservation.setReservationStatus(ReservationStatus.CANCELED);
                    reservationService.persistReservation(reservation);
                }
            }
        }

    }

    @Override
    public List<Restaurant> getAllOtherRestaurantsForRestorer(int userId) {
       return restaurantDAO.getAllOtherRestaurantsForRestorer(userId);
    }


}
