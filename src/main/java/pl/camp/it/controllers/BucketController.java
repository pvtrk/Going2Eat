package pl.camp.it.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.camp.it.model.Image;
import pl.camp.it.model.Restaurant;
import pl.camp.it.service.IImageService;
import pl.camp.it.service.IRestaurantService;
import pl.camp.it.service.amazon.AmazonClient;

@Controller
public class BucketController {
    @Autowired
    IImageService imageService;
    @Autowired
    AmazonClient amazonClient;
    @Autowired
    IRestaurantService restaurantService;

    /*private AmazonClient amazonClient;

    @Autowired
    BucketController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }*/

    @GetMapping(value="/uploadFile/{id}")
    public String getUploadingScreen(@PathVariable int id, Model model) {
        model.addAttribute("restaurant", restaurantService.getRestaurantById(id));
        model.addAttribute("image", new Image());
        return "uploadFile";
    }

    @PostMapping(value="/uploadFile/{id}")
    public String uploadImage(@PathVariable int id, Model model,
                              @RequestPart(value = "file") MultipartFile file,
                              @ModelAttribute Image image) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("result", imageService.saveImageInput(file, id, image));
        return "uploadFile";
    }
}
