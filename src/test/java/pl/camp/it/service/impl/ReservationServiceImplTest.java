package pl.camp.it.service.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.camp.it.configuration.AppConfigurationTest;
import pl.camp.it.dao.IPromotionDAO;
import pl.camp.it.dao.IReservationDAO;
import pl.camp.it.dao.IRestaurantDAO;
import pl.camp.it.model.*;
import pl.camp.it.service.IBlockadeService;
import pl.camp.it.service.IReservationService;
import pl.camp.it.session.SessionObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigurationTest.class})
public class ReservationServiceImplTest {
    @Autowired
    IReservationDAO reservationDAO;
    @Autowired
    IReservationService reservationService;
    @Autowired
    IRestaurantDAO restaurantDAO;
    @Autowired
    IBlockadeService blockadeService;
    @Autowired
    SessionFactory sessionFactory;
    @MockBean
    SessionObject sessionObject;

    /*@Test
    public void getReservationsForRestorer() {
        User restorer = generateUserForTest();
        int expectedResultListSize = 4;

        Mockito.when(session.createQuery("FROM treservation WHERE restaurantId = 1").list()).thenReturn(generateReservationListForTest());
        Mockito.when(restaurantDAO.getRestaurantsByUserId(restorer.getId())).thenReturn(generateRestaurantListForTest());
        List<Reservation> reservations = reservationService.getReservationsForRestorer(restorer);

        Assert.assertEquals(expectedResultListSize, reservations.size());

    }*/

    @Test
    public void doComplexReservationActionTest() {
        Restaurant restaurant = generateRestaurantForTest();
        String reservationTime = "2020-08-17 15:00";
        int guestNumber = 5;
        String comments = "";

        Mockito.when(this.reservationDAO.getReservationsByRestaurantId(restaurant.getId())).thenReturn(new ArrayList<>());
        Mockito.when(this.sessionObject.getUser()).thenReturn(generateUserForTest());

        boolean result = reservationService.doComplexReservationAction(restaurant, guestNumber, comments, reservationTime);

        Assert.assertTrue(result);
    }

    @Test
    public void doComplexReservationActionWithNullRestaurantTest() {
        Restaurant restaurant = null;
        String reservationTime = "2020-08-17 15:00";
        int guestNumber = 5;
        String comments = "";

        Mockito.when(this.reservationDAO.getReservationsByRestaurantId(5)).thenReturn(new ArrayList<>());
        Mockito.when(this.sessionObject.getUser()).thenReturn(generateUserForTest());

        boolean result = reservationService.doComplexReservationAction(restaurant, guestNumber, comments, reservationTime);

        Assert.assertFalse(result);
    }

    @Test(expected = DateTimeParseException.class)
    public void doComplexReservationActionWithWrongDateTest() {
        Restaurant restaurant = generateRestaurantForTest();
        String reservationTime = "2020-089-17 15:00";
        int guestNumber = 5;
        String comments = "";

        Mockito.when(this.reservationDAO.getReservationsByRestaurantId(restaurant.getId())).thenReturn(new ArrayList<>());
        Mockito.when(this.sessionObject.getUser()).thenReturn(generateUserForTest());

        boolean result = reservationService.doComplexReservationAction(restaurant, guestNumber, comments, reservationTime);

    }

    @Test
    public void isBlockedTest() {
        List<Blockade> blockades = generateBlockadeListForTest();
        String startTime = "2020-08-30 17:30";

        Mockito.when(this.blockadeService.getActiveBlockadesForRestaurant(3)).thenReturn(blockades);

        boolean result = reservationService.isBlocked(3, startTime);

        Assert.assertFalse(result);

    }
    @Test
    public void isBlockedWhenBlockedStartTimeTest() {
        List<Blockade> blockades = generateBlockadeListForTest();
        String startTime = "2020-09-21 17:30";

        Mockito.when(this.blockadeService.getActiveBlockadesForRestaurant(3)).thenReturn(blockades);

        boolean result = reservationService.isBlocked(3, startTime);

        Assert.assertTrue(result);

    }

    @Test(expected = DateTimeParseException.class)
    public void isBlockedWhenWrongDateFormatTest() {
        List<Blockade> blockades = generateBlockadeListForTest();
        String startTime = " ";

        Mockito.when(this.blockadeService.getActiveBlockadesForRestaurant(3)).thenReturn(blockades);

        boolean result = reservationService.isBlocked(3, startTime);

        Assert.assertTrue(result);

    }

    private List<Blockade> generateBlockadeListForTest() {
        List<Blockade> blockades = new ArrayList<>();
        Blockade blockade = new Blockade();
        blockade.setActive(true);
        blockade.setRestaurant(new Restaurant());
        blockade.setUserId(3);
        blockade.setStartDate(LocalDateTime.of(2020, 9, 20, 17, 30));
        blockade.setEndDate(LocalDateTime.of(2020, 9, 25, 17, 30));
        blockades.add(blockade);

        return blockades;
    }

    private User generateUserForTest() {
        User user = new User();
        user.setPass("abc");
        user.setLogin("abc");
        user.setName("afdafd");
        user.setRole(UserRole.RESTORER);
        user.setSurname("ldgjadhgja");
        user.setId(3);
        return user;
    }

    private List<Reservation> generateReservationListForTest() {
        List<Reservation> reservations = generateRestaurantListForTest()
                .stream()
                .map(x -> x.getReservations())
                .flatMap(list -> list.stream())
                .collect(Collectors.toList());
        return reservations;
    }

    private Restaurant generateRestaurantForTest() {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantStatus(RestaurantStatus.ACTIVE);
        restaurant.setUserId(3);
        restaurant.setAdress("gfagadfga");
        restaurant.setCity("gfadgdga");
        restaurant.setName("gkfdgjadga");
        restaurant.setPlaces(23);
        restaurant.setCuisineType("gfdajkgjadgad");
        restaurant.setId(1);
        restaurant.setPromotions(new ArrayList<>());
        restaurant.setBlockades(new ArrayList<>());
        restaurant.setImages(new ArrayList<>());
        return restaurant;
    }

    private List<Restaurant> generateRestaurantListForTest() {
        List<Restaurant> result = new ArrayList<>();
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantStatus(RestaurantStatus.ACTIVE);
        restaurant.setUserId(3);
        restaurant.setAdress("gfagadfga");
        restaurant.setCity("gfadgdga");
        restaurant.setName("gkfdgjadga");
        restaurant.setPlaces(23);
        restaurant.setCuisineType("gfdajkgjadgad");
        restaurant.setId(1);
        restaurant.setPromotions(new ArrayList<>());
        restaurant.setBlockades(new ArrayList<>());
        restaurant.setImages(new ArrayList<>());


        Reservation r1 = new Reservation();
        r1.setPast(false);
        r1.setReservationStatus(ReservationStatus.WAITING);
        r1.setComments("gdgdasga");
        r1.setGuestsQuantity(5);
        r1.setRestaurant(restaurant);

        Reservation r2 = new Reservation();
        r2.setPast(false);
        r2.setReservationStatus(ReservationStatus.WAITING);
        r2.setComments("gdgdasga");
        r2.setGuestsQuantity(10);
        r2.setRestaurant(restaurant);

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        reservations.add(r2);
        restaurant.setReservations(reservations);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setRestaurantStatus(RestaurantStatus.ACTIVE);
        restaurant2.setUserId(3);
        restaurant2.setAdress("gfagadfga");
        restaurant2.setCity("gfadgdga");
        restaurant2.setName("gweeeeeeer");
        restaurant2.setPlaces(23);
        restaurant2.setCuisineType("gfdajkgjadgad");
        restaurant2.setId(2);
        restaurant2.setPromotions(new ArrayList<>());
        restaurant2.setBlockades(new ArrayList<>());
        restaurant2.setImages(new ArrayList<>());


        Reservation r3 = new Reservation();
        r3.setPast(false);
        r3.setReservationStatus(ReservationStatus.WAITING);
        r3.setComments("gdgdasga");
        r3.setGuestsQuantity(5);
        r3.setRestaurant(restaurant);

        Reservation r4 = new Reservation();
        r4.setPast(false);
        r4.setReservationStatus(ReservationStatus.WAITING);
        r4.setComments("gdgdasga");
        r4.setGuestsQuantity(10);
        r4.setRestaurant(restaurant);

        List<Reservation> reservations2 = new ArrayList<>();
        reservations.add(r3);
        reservations.add(r4);
        restaurant2.setReservations(reservations2);

        result.add(restaurant);
        result.add(restaurant2);
        return result;

    }
}
