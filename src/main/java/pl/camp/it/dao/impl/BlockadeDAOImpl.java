package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IBlockadeDAO;
import pl.camp.it.model.Blockade;

import java.util.List;
@Repository
public class BlockadeDAOImpl implements IBlockadeDAO {
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public void persistBlockade(Blockade blockade) {
        Transaction tx = null;
        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(blockade);
            tx.commit();
            session.close();
        } catch(Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public Blockade getBlockadeById(int id) {
        Session session = sessionFactory.openSession();
        Blockade blockade = session.createQuery("FROM tblockade WHERE id = " + id, Blockade.class).uniqueResult();
        session.close();
        return blockade;
    }

    @Override
    public List<Blockade> getBlockadesByRestaurantId(int id) {
        Session session = sessionFactory.openSession();
        List<Blockade> blockades = session.createQuery("FROM tblockade WHERE restaurantId = " + id).list();
        session.close();
        return blockades;
    }

    @Override
    public List<Blockade> getActiveBlockadesForRestaurant(int id) {
        Session session = sessionFactory.openSession();
        List<Blockade> blockades = session.createQuery("FROM tblockade WHERE restaurantId = " + id + "AND active = " + 1).list();
        session.close();
        return blockades;
    }

    @Override
    public List<Blockade> getAllActiveBlockades() {
        Session session = sessionFactory.openSession();
        List<Blockade> blockades = session.createQuery("FROM tblockade WHERE active = " + 1).list();
        session.close();
        return blockades;
    }
}
