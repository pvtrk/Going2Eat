package pl.camp.it.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.camp.it.configuration.AppConfigurationTest;
import pl.camp.it.dao.IPromotionDAO;
import pl.camp.it.model.Promotion;
import pl.camp.it.model.PromotionStatus;
import pl.camp.it.model.Restaurant;
import pl.camp.it.service.IPromotionService;
import pl.camp.it.utils.RegexChecker;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigurationTest.class})
public class PromotionsServiceImplTest {
    @Autowired
    IPromotionService promotionService;
    @Autowired
    IPromotionDAO promotionDAO;
    @Autowired
    RegexChecker regexChecker;

    @Test
    public void createPromotionTest() {
        Promotion pro = generatePromotionForTest();
        String startDate = "2020-08-17";
        String endDate = "2020-09-17";

        Mockito.when(this.regexChecker.getDescriptionRegex()).thenReturn("^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9_@.\\/#&*!'+\\-,\\s]{5,150}$");
        Mockito.when(this.regexChecker.checkInput(pro.getDescription(), this.regexChecker.getDescriptionRegex())).thenReturn(true);

        boolean result = this.promotionService.createPromotion(pro,startDate, endDate);
        Assert.assertTrue(result);
    }

    @Test
    public void createPromotionWithWrongDatesTest() {
        Promotion pro = generatePromotionForTest();
        String startDate = "2020-09-17";
        String endDate = "2020-08-17";

        Mockito.when(this.regexChecker.getDescriptionRegex()).thenReturn("^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9_@.\\/#&*!'+\\-,\\s]{5,150}$");
        Mockito.when(this.regexChecker.checkInput(pro.getDescription(), this.regexChecker.getDescriptionRegex())).thenReturn(true);

        boolean result = this.promotionService.createPromotion(pro,startDate, endDate);
        Assert.assertFalse(result);
    }

    @Test
    public void createPromotionWithNullPromotionTest() {
        Promotion pro = null;
        String startDate = "2020-08-17";
        String endDate = "2020-09-17";
        boolean result = this.promotionService.createPromotion(pro,startDate, endDate);
        Mockito.when(this.regexChecker.getDescriptionRegex()).thenReturn("^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9_@.\\/#&*!'+\\-,\\s]{5,150}$");
        Mockito.when(this.regexChecker.checkInput("jfdfsd", this.regexChecker.getDescriptionRegex())).thenReturn(true);


        Assert.assertFalse(result);
    }

    @Test
    public void getDistinctedPromotionsTest() {
        int expectedSize = 1;
        Mockito.when(this.promotionDAO.getAllPromotions()).thenReturn(generatePromotionsListForTest());
        List<Promotion> result = promotionService.getDistinctedPromotions();
        Assert.assertEquals(result.size(), expectedSize);
    }

    @Test
    public void getDistinctedPromotionsWhenListIsEmptyTest() {
        int expectedSize = 0;
        Mockito.when(promotionDAO.getAllPromotions()).thenReturn(new ArrayList<Promotion>());
        List<Promotion> result = promotionService.getDistinctedPromotions();
        Assert.assertEquals(result.size(), expectedSize);
    }






    private Promotion generatePromotionForTest() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        Promotion promotion = new Promotion();
        promotion.setDistinction(true);
        promotion.setStatus(PromotionStatus.ACTIVE);
        promotion.setRestaurant(restaurant);
        promotion.setDescription("trtarga");
        promotion.setPrice(50);
        return promotion;
    }
    private List<Promotion> generatePromotionsListForTest() {
        List<Promotion> result = new ArrayList<>();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        Promotion promotion = new Promotion();
        promotion.setDistinction(true);
        promotion.setStatus(PromotionStatus.ACTIVE);
        promotion.setRestaurant(restaurant);
        promotion.setDescription("trtarga");
        promotion.setPrice(50);

        Promotion promotion2 = new Promotion();
        promotion2.setDistinction(false);
        promotion2.setStatus(PromotionStatus.ACTIVE);
        promotion2.setRestaurant(restaurant);
        promotion2.setDescription("trqgreqg");
        promotion2.setPrice(750);

        Promotion promotion3 = new Promotion();
        promotion3.setDistinction(false);
        promotion3.setStatus(PromotionStatus.ACTIVE);
        promotion3.setRestaurant(restaurant);
        promotion3.setDescription("trqggdareqg");
        promotion3.setPrice(550);
        result.add(promotion);
        result.add(promotion2);
        result.add(promotion3);
        return result;
    }
}
