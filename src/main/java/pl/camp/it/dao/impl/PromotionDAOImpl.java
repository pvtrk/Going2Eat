package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IPromotionDAO;
import pl.camp.it.model.Promotion;

import java.util.List;

@Repository
public class PromotionDAOImpl implements IPromotionDAO {
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public void persistPromotion(Promotion promotion) {
        Transaction tx = null;
        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(promotion);
            tx.commit();
            session.close();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public Promotion getPromotionById(int id) {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM tpromotion WHERE id = " + id, Promotion.class).uniqueResult();
    }

    @Override
    public List<Promotion> getPromotionsSortedByDate() {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM tpromotion WHERE status = 'ACTIVE' ORDER BY startDate").list();
    }

    @Override
    public List<Promotion> getAllPromotions() {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM tpromotion").list();
    }

    @Override
    public List<Promotion> getPromotionsByRestaurantId(int id) {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM tpromotion WHERE status = 'ACTIVE' AND restaurantId = " + id).list();
    }
}
