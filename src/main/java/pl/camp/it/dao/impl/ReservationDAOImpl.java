package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IReservationDAO;
import pl.camp.it.model.Reservation;

import java.util.List;

@Repository
public class ReservationDAOImpl implements IReservationDAO {
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public void persistReservation(Reservation reservation) {
        Transaction tx = null;
        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(reservation);
            tx.commit();
            session.close();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public List<Reservation> getReservationsByUserId(int id) {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM treservation WHERE userId = " + id).list();
    }

    @Override
    public List<Reservation> getReservationsByRestaurantId(int id) {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM treservation WHERE restaurantId = " + id).list();
    }
}
