package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IReservationDAO;
import pl.camp.it.model.Reservation;

import java.util.ArrayList;
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
    public Reservation getReservationById(int id) {
        Session session = sessionFactory.openSession();
        Reservation reservation = session.createQuery("FROM treservation WHERE id = " + id, Reservation.class).uniqueResult();
        session.close();
        return reservation;
    }

    @Override
    public List<Reservation> getAllReservations() {
        Session session = sessionFactory.openSession();
        List<Reservation> reservations = session.createQuery("FROM treservation").list();
        session.close();
        return reservations;
    }

    @Override
    public List<Reservation> getReservationsByUserId(int id) {
        Session session = sessionFactory.openSession();
        List<Reservation> reservations = session.createQuery("FROM treservation WHERE userId = " + id).list();
        session.close();
        return reservations;
    }

    @Override
    public List<Reservation> getReservationsByRestaurantId(int id) {
        Session session = sessionFactory.openSession();
        List<Reservation> reservations = session.createQuery("FROM treservation WHERE restaurantId = " + id).list();
        session.close();
        return reservations;
    }

    @Override
    public List<Reservation> getActiveReservationsForUser(int id) {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM treservation WHERE userId = " + id + " AND reservationStatus = " + "'ACTIVE'").list();
    }

    @Override
    public List<Reservation> getFutureReservations() {
        Session session = sessionFactory.openSession();
        List<Reservation> result = session.createQuery("FROM treservation WHERE past = " + 0).list();
        session.close();
        return result;
    }

    @Override
    public List<Reservation> getWaitingReservationsForRestaurant(int id) {
        Session session = sessionFactory.openSession();
        List<Reservation> reservations = session.createQuery
                ("FROM treservation WHERE restaurantId = " + id + "AND reservationStatus = " + "'WAITING'").list();
        session.close();
        return reservations;
    }

    @Override
    public List<Reservation> getAcceptedReservationsForRestaurant(int id) {
        Session session = sessionFactory.openSession();
        List<Reservation> reservations = session.createQuery
                ("FROM treservation WHERE restaurantId = " + id + "AND reservationStatus = " + "'ACCEPTED'").list();
        session.close();
        return reservations;
    }

    @Override
    public List<Reservation> getDeclinedReservationsForRestaurant(int id) {
        Session session = sessionFactory.openSession();
        List<Reservation> reservations = session.createQuery
                ("FROM treservation WHERE restaurantId = " + id + "AND reservationStatus = " + "'DECLINED'").list();
        session.close();
        return reservations;
    }

    @Override
    public List<Reservation> getCanceledReservationsForRestaurant(int id) {
        Session session = sessionFactory.openSession();
        List<Reservation> reservations = session.createQuery
                ("FROM treservation WHERE restaurantId = " + id + "AND reservationStatus = 'CANCELED'").list();
        session.close();
        return reservations;
    }
}
