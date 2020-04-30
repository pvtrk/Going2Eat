package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IRestaurantDAO;
import pl.camp.it.model.Restaurant;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestaurantDAOImpl implements IRestaurantDAO {
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public void persistRestaurant(Restaurant restaurant) {
        Transaction tx = null;
        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(restaurant);
            session.flush();
            tx.commit();
            session.close();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM trestaurant").list();
    }

    @Override
    public List<Restaurant> getRestaurantsByUserId(int id) {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM trestaurant WHERE userId = " + id).list();
    }

    @Override
    public Restaurant getRestaurantById(int id) {
        Session session = sessionFactory.openSession();
        Restaurant restaurant =  session.createQuery("FROM trestaurant WHERE id = " + id, Restaurant.class).uniqueResult();
        session.close();
        return restaurant;
    }

    @Override
    public void addFavouriteRestaurant(int userId, int restaurantId) {
        Transaction tx = null;
        try {
        Session session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.createNativeQuery("INSERT INTO tfavouriterestaurant (userId, restaurantId) VALUES (" + userId + ", " + restaurantId +")").executeUpdate();
        tx.commit();
        session.close();
        } catch (Exception E) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public List<Restaurant> getFavouriteRestaurants(int userId) {
        List<Restaurant> result = new ArrayList<>();
        Session session = sessionFactory.openSession();
        List<Integer> restaurantId = session.createNativeQuery("select restaurantId from tfavouriterestaurant where userId = " + userId).list();
        for(Integer i : restaurantId) {
            result.add(this.getRestaurantById(i));
        }
        return result;
    }

    @Override
    public List<Restaurant> getActiveRestaurants() {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM trestaurant WHERE restaurantStatus = 'ACTIVE'").list();
    }
}
