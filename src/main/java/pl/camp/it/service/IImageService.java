package pl.camp.it.service;

import org.springframework.web.multipart.MultipartFile;
import pl.camp.it.model.Image;

import java.util.List;

public interface IImageService {
    void persistImage (Image image);
    List<Image> getImagesForRestaurant(int restaurantId);
    Image getProfilePictureForRestaurant(int restaurantId);
    Image getImageById(int imageId);
    String saveImageInput(MultipartFile file, int id, Image image);
    List<Image> getRestaurantsMenu(int restaurantId);
}
