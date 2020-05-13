package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IUserDAO;
import pl.camp.it.model.User;

import java.util.List;

@Repository
public class UserDAOImpl implements IUserDAO {
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public void persistUser(User user) {
        Transaction tx = null;
        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(user);
            tx.commit();
            session.close();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        List<User> users =  session.createQuery("FROM tuser").list();
        session.close();
        return users;
    }

    @Override
    public User getUserByLogin(String login) {
        Session session = sessionFactory.openSession();
        User user = session.createQuery("FROM tuser WHERE login ='" + login + "'", User.class).uniqueResult();
        session.close();
        return user;
    }

    @Override
    public User getUserById(int id) {
        Session session = sessionFactory.openSession();
        User user = session.createQuery("FROM tuser WHERE id = "+ id, User.class).uniqueResult();
        session.close();
        return user;
    }
}
