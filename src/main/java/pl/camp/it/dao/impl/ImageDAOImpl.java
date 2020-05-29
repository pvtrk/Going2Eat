package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IImageDAO;
import pl.camp.it.model.Image;

import java.util.List;
@Repository
public class ImageDAOImpl implements IImageDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistImage(Image image) {
        Transaction tx = null;
        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(image);
            tx.commit();
            session.close();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public List<Image> getImagesForRestaurant(int restaurantId) {
        Session session = sessionFactory.openSession();
        List<Image> images = session.createQuery("FROM timage WHERE restaurantId = "
                + restaurantId + "AND active = " + 1 + "AND menu = " + 0)
                .list();
        session.close();
        return images;
    }

    @Override
    public Image getProfilePictureForRestaurant(int restaurantId) {
        Session session = sessionFactory.openSession();
        Image image = session
                .createQuery("FROM timage WHERE restaurantId = "
                        + restaurantId + " AND profilePicture = " + 1 + "AND active = " + 1, Image.class)
                .uniqueResult();
        session.close();
        return image;
    }

    @Override
    public List<Image> getMenuForRestaurant(int restaurantId) {
        Session session = sessionFactory.openSession();
        List<Image> images = session
                .createQuery("FROM timage WHERE restaurantId = "
                        + restaurantId + "AND menu = " + 1 + "AND active = " + 1).list();
        session.close();
        return images;
    }
}
