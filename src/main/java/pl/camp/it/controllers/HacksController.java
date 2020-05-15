package pl.camp.it.controllers;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.camp.it.model.*;
import pl.camp.it.service.IPromotionService;
import pl.camp.it.service.IRestaurantService;
import pl.camp.it.service.IUserService;

import java.time.LocalDate;

@Controller
public class HacksController {
    @Autowired
    IUserService userService;
    @Autowired
    IRestaurantService restaurantService;
    @Autowired
    IPromotionService promotionService;
    @GetMapping(value = "/170896")
    public String hackMyWorld() {
        User user = new User();
        user.setLogin("admin11");
        user.setPass(DigestUtils.md5Hex("admin11"));
        user.setName("Patryk");
        user.setSurname("Łącki");
        user.setRole(UserRole.ADMIN);
        userService.persistUser(user);

        User user2 = new User();
        user2.setLogin("user11");
        user2.setPass(DigestUtils.md5Hex("user11"));
        user2.setName("Patrik");
        user2.setSurname("Patrik");
        user2.setRole(UserRole.USER);
        userService.persistUser(user2);
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
        restaurant.setCity("Kraków");
        restaurant.setAdress("ul. Grodzka 2");
        restaurant.setRestaurantStatus(RestaurantStatus.ACTIVE);
        restaurantService.persistRestaurant(restaurant);

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setName("Wentzl");
        restaurant1.setDescription("Jedna z najstarszych krakowskich restauracji, usytuowana w Kamienicy Pod Obrazem. Założona w roku 1792 do dziś serwuje gościom najwyższej jakości danie kuchni polskiej.");
        restaurant1.setPlaces(60);
        restaurant1.setUserId(2);
        restaurant1.setCuisineType("Polska");
        restaurant1.setCity("Kraków");
        restaurant1.setAdress("Rynek Główny 9");
        restaurant1.setRestaurantStatus(RestaurantStatus.ACTIVE);
        restaurantService.persistRestaurant(restaurant1);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setName("Bottiglieria1881");
        restaurant2.setDescription("Krakowska butikowa restauracja, w której znajdą Państwo 500 etykiet wina. Miejsce na krakowskim Kazimierzu, dla którego jakość jest najwyższą wartośćia.");
        restaurant2.setPlaces(24);
        restaurant2.setUserId(2);
        restaurant2.setCuisineType("Europejska, fine dining");
        restaurant2.setCity("Kraków");
        restaurant2.setAdress("ul. Bocheńska 5");
        restaurant2.setRestaurantStatus(RestaurantStatus.ACTIVE);
        restaurantService.persistRestaurant(restaurant2);

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setName("Boscaiola");
        restaurant3.setDescription("Włoska kuchnia w sercu Krakowa. Dania skomponowane są w oparciu o regionalne produkty.");
        restaurant3.setPlaces(120);
        restaurant3.setUserId(2);
        restaurant3.setCuisineType("Włoska");
        restaurant3.setCity("Kraków");
        restaurant3.setAdress("ul. Szpitalna 42");
        restaurant3.setRestaurantStatus(RestaurantStatus.ACTIVE);
        restaurantService.persistRestaurant(restaurant3);


        return "main";

    }
    @GetMapping(value="/tralala")
    public String makeNormalUser() {
        User user = new User();
        user.setName("Michał");
        user.setSurname("Michalkiewicz");
        user.setRole(UserRole.USER);
        user.setLogin("user");
        user.setPass("user");
        this.userService.persistUser(user);
        return "redirect:/main";
    }

    @GetMapping(value="/rampampam")
    public String hackPromotion(){
        LocalDate date = LocalDate.now();
        Promotion promotion = new Promotion();
        promotion.setRestaurant(restaurantService.getRestaurantById(1));
        promotion.setDescription("Dwa kieliszki prosecco w cenie jednego");
        promotion.setPrice(10);
        promotion.setStartDate(date);
        promotion.setEndDate(date.plusWeeks(1));
        promotion.setStatus(PromotionStatus.ACTIVE);
        promotion.setDistinction(true);
        this.promotionService.persistPromotion(promotion);
        return "main";
    }

}
