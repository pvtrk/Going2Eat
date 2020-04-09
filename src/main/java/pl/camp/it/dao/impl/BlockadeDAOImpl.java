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
    public List<Blockade> getBlockadesByRestaurantId(int id) {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM tblockade WHERE blockades = " + id).list();
    }
}
