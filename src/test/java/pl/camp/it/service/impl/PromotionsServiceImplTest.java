package pl.camp.it.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.camp.it.configuration.AppConfigurationTest;
import pl.camp.it.model.Promotion;
import pl.camp.it.model.PromotionStatus;
import pl.camp.it.model.Restaurant;
import pl.camp.it.service.IPromotionService;
import pl.camp.it.utils.RegexChecker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigurationTest.class})
public class PromotionsServiceImplTest {
    @Autowired
    IPromotionService promotionService;
    @Autowired
    RegexChecker regexChecker;

    @Test
    public void createPromotionTest() {
        Promotion pro = generatePromotionForTest();
        String startDate = "2020-08-17";
        String endDate = "2020-09-17";

        Mockito.when(this.regexChecker.getDescriptionRegex()).thenReturn("^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9_@.\\/#&*!+-.\\s]{5,150}$");
        Mockito.when(this.regexChecker.checkInput(pro.getDescription(), this.regexChecker.getDescriptionRegex())).thenReturn(true);

        boolean result = this.promotionService.createPromotion(pro,startDate, endDate);
        Assert.assertTrue(result);
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
}
