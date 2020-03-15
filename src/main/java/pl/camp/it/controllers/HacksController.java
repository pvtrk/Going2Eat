package pl.camp.it.controllers;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.camp.it.model.Restaurant;
import pl.camp.it.model.RestaurantStatus;
import pl.camp.it.model.User;
import pl.camp.it.model.UserRole;
import pl.camp.it.service.IRestaurantService;
import pl.camp.it.service.IUserService;

@Controller
public class HacksController {
    @Autowired
    IUserService userService;
    @Autowired
    IRestaurantService restaurantService;
    @GetMapping(value = "/170896")
    public String hackMyWorld() {
        User user = new User();
        user.setLogin("admin1");
        user.setPass(DigestUtils.md5Hex("admin1"));
        user.setName("Patryk");
        user.setSurname("Łącki");
        user.setRole(UserRole.ADMIN);
        userService.persistUser(user);
        return "/login";
    }

    @GetMapping(value = "/g200")
    public String hackMyRestaurants() {
        User user = new User();
        user.setName("Patryk");
        user.setSurname("Łącki");
        user.setLogin("patryk");
        user.setPass(DigestUtils.md5Hex("patryk"));
        user.setRole(UserRole.RESTORER);
        userService.persistUser(user);

        Restaurant restaurant = new Restaurant();
        restaurant.setName("Fiorentina");
        restaurant.setDescription("Smaki Fiorentiny\n" +
                "W naszej karcie znajdą Państwo dania bazujące na świeżych, nieprzetworzonych, niezawierających konserwantów lokalnych składnikach. Dania te podane są w nowoczesnych aranżacjach, nieoczywistych połączeniach smaków. Potrawy przygotowane są z najwyższą starannością, z pełną dbałością o szczegóły i składniki serwowane w kilku odsłonach w czasie jednego kulinarnego doświadczenia. " +
                "Jedną z naszych specjalności jest sezonowana wołowina, przygotowywana na grillu opalanym węglem drzewnym. Serwujemy, wyjątkowy stek po florencku, a sekret bistecca alla fiorentina tkwi w wyjątkowym sposobie cięcia mięsa i jego jakości.");
        restaurant.setPlaces(80);
        restaurant.setUserId(2);
        restaurant.setCuisineType("Polska");
        restaurant.setRestaurantStatus(RestaurantStatus.ACTIVE);
        restaurantService.persistRestaurant(restaurant);

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setName("Wentzl");
        restaurant1.setDescription("Jedna z najstarszych krakowskich restauracji, usytuowana w Kamienicy Pod Obrazem. Założona w roku 1792 do dziś serwuje gościom najwyższej jakości danie kuchni polskiej.");
        restaurant1.setPlaces(60);
        restaurant1.setUserId(2);
        restaurant1.setCuisineType("Polska");
        restaurant1.setRestaurantStatus(RestaurantStatus.ACTIVE);
        restaurantService.persistRestaurant(restaurant1);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setName("Bottiglieria1881");
        restaurant2.setDescription("Krakowska butikowa restauracja, w której znajdą Państwo 500 etykiet wina. Miejsce na krakowskim Kazimierzu, dla którego jakość jest najwyższą wartośćia.");
        restaurant2.setPlaces(24);
        restaurant2.setUserId(2);
        restaurant2.setCuisineType("Europejska, fine dining");
        restaurant2.setRestaurantStatus(RestaurantStatus.ACTIVE);
        restaurantService.persistRestaurant(restaurant2);

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setName("Boscaiola");
        restaurant3.setDescription("Włoska kuchnia w sercu Krakowa. Dania skomponowane są w oparciu o regionalne produkty.");
        restaurant3.setPlaces(120);
        restaurant3.setUserId(2);
        restaurant3.setCuisineType("Włoska");
        restaurant3.setRestaurantStatus(RestaurantStatus.ACTIVE);
        restaurantService.persistRestaurant(restaurant3);


        return "main";

    }
}
