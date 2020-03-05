package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IRestaurantDAO;
import pl.camp.it.model.Restaurant;

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
            tx = session.getTransaction();
            session.saveOrUpdate(restaurant);
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
    public List<Restaurant> getRestaurantByUserId(int id) {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM trestaurant WHERE userId = " + id).list();
    }

    @Override
    public Restaurant getRestaurantById(int id) {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM trestaurant WHERE id = " + id, Restaurant.class).uniqueResult();
    }
}
