package pl.camp.it.dao;

import pl.camp.it.model.Image;

import java.util.List;

public interface IImageDAO {
    void persistImage (Image image);
    List<Image> getImagesForRestaurant(int restaurantId);
    Image getProfilePictureForRestaurant(int restaurantId);
    List<Image> getMenuForRestaurant(int restaurantId);
}
