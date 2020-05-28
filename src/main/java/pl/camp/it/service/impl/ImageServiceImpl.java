package pl.camp.it.service.impl;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.camp.it.dao.IImageDAO;
import pl.camp.it.dao.IRestaurantDAO;
import pl.camp.it.model.Image;
import pl.camp.it.model.Restaurant;
import pl.camp.it.service.IImageService;
import pl.camp.it.service.IRestaurantService;
import pl.camp.it.service.amazon.AmazonClient;


import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ImageServiceImpl implements IImageService {
    @Autowired
    IRestaurantService restaurantService;
    @Autowired
    AmazonClient amazonClient;
    @Autowired
    IImageDAO imageDAO;
    @Override
    public void persistImage(Image image) {
        imageDAO.persistImage(image);
    }

    @Override
    public List<Image> getImagesForRestaurant(int restaurantId) {
        return imageDAO.getImagesForRestaurant(restaurantId);
    }

    @Override
    public Image getProfilePictureForRestaurant(int restaurantId) {
        return imageDAO.getProfilePictureForRestaurant(restaurantId);
    }

    @Override
    public String saveImageInput(MultipartFile file, int id, Image image) {
        File inputFile = null;
        try {
            inputFile = amazonClient.convertMultiPartToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (inputFile != null) {
            try (InputStream input = file.getInputStream()) {
                String mimeType = Magic.getMagicMatch(inputFile, false).getMimeType();
                if (mimeType.startsWith("image/")) {
                    String fileUrl = this.amazonClient.uploadFile(file);
                    image.setUrl(fileUrl);
                    Restaurant restaurant = restaurantService.getRestaurantById(id);
                    if(restaurant != null) {
                        image.setRestaurant(restaurant);
                    }
                    persistImage(image);
                    return "Udało Ci się dodać zdjęcie";
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MagicException e) {
                e.printStackTrace();
            } catch (MagicParseException e) {
                e.printStackTrace();
            } catch (MagicMatchNotFoundException e) {
                e.printStackTrace();
            }
        } return "Nie udało się dodać zdjęcia";
    }
}
