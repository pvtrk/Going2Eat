package pl.camp.it.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.camp.it.configuration.AppConfigurationTest;
import pl.camp.it.model.*;
import pl.camp.it.service.IBlockadeService;
import pl.camp.it.service.IRestaurantService;
import pl.camp.it.session.SessionObject;



import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigurationTest.class})
public class BlockadeServiceImplTest {
    @Autowired
    IBlockadeService blockadeService;
    @MockBean
    IRestaurantService restaurantService;
    @MockBean
    SessionObject sessionObject;

    @Test
    public void createBlockadeTest() {
        User user = generateUserForTest();
        Mockito.when(this.restaurantService.getRestaurantById(2)).thenReturn(generateRestaurantForTest());
        Mockito.when(this.sessionObject.getUser()).thenReturn(user);
        String date1 = "2020-08-17 11:30";
        String date2 = "2020-08-18 11:30";

        boolean result = this.blockadeService.createBlockade(2, date1, date2);

        Assert.assertTrue(result);
    }

    @Test
    public void createBlockadeTestWithWrongDates() {
        User user = generateUserForTest();
        Mockito.when(this.restaurantService.getRestaurantById(2)).thenReturn(generateRestaurantForTest());
        Mockito.when(this.sessionObject.getUser()).thenReturn(user);
        String date1 = "2020-08-18 11:30";
        String date2 = "2020-08-17 11:30";

        boolean result = this.blockadeService.createBlockade(2, date1, date2);

        Assert.assertFalse(result);
    }

    @Test(expected = Blockade.BlockadeValidationException.class)
    public void createBlockadeWithNullRestaurantTest() {
        User user = generateUserForTest();
        Mockito.when(this.restaurantService.getRestaurantById(2)).thenReturn(null);
        Mockito.when(this.sessionObject.getUser()).thenReturn(user);
        String date1 = "2020-08-17 11:30";
        String date2 = "2020-08-18 11:30";

        boolean result = this.blockadeService.createBlockade(2, date1, date2);

    }

    @Test(expected = Blockade.BlockadeValidationException.class)
    public void createBlockadeWithNullUserTest() {
        User user = generateUserForTest();
        Mockito.when(this.restaurantService.getRestaurantById(2)).thenReturn(generateRestaurantForTest());
        Mockito.when(this.sessionObject.getUser()).thenReturn(null);
        String date1 = "2020-08-17 11:30";
        String date2 = "2020-08-18 11:30";

        boolean result = this.blockadeService.createBlockade(2, date1, date2);

    }

    private Restaurant generateRestaurantForTest() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(2);
        restaurant.setName("trdkgadjgka");
        restaurant.setAdress("papapa");
        restaurant.setCity("krkr");
        restaurant.setRestaurantStatus(RestaurantStatus.ACTIVE);
        restaurant.setDescription("prkgjfkg");
        restaurant.setPlaces(30);
        restaurant.setCuisineType("kjgkdjagld");
        restaurant.setUserId(3);
        restaurant.setPromotions(new ArrayList<>());
        return restaurant;
    }

    private User generateUserForTest() {
        User user = new User();
        user.setRole(UserRole.RESTORER);
        user.setPass("digkajdgkad");
        user.setLogin("ogdajgkldj");
        user.setId(3);
        user.setName("gfldjgkladj");
        user.setSurname("gidfajgadk");
        return user;
    }
}
