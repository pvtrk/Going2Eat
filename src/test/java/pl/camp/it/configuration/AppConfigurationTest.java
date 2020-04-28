package pl.camp.it.configuration;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;
import pl.camp.it.dao.*;
import pl.camp.it.session.SessionObject;
import pl.camp.it.utils.RegexChecker;


@Configuration
@ComponentScan({"pl.camp.it.controllers", "pl.camp.it.service", "pl.camp.it.session"})
public class AppConfigurationTest {
    @Bean
    @SessionScope
    public SessionObject sessionObject(){
        return new SessionObject();
    }

    @Bean
    public IBlockadeDAO blockadeDAO() {
        return Mockito.mock(IBlockadeDAO.class);
    }
    @Bean
    public IRestaurantDAO restaurantDAO() {
        return Mockito.mock(IRestaurantDAO.class);
    }
    @Bean
    public IReservationDAO reservationDAO() {
        return Mockito.mock(IReservationDAO.class);
    }
    @Bean
    public IUserDAO userDAO() {
        return Mockito.mock(IUserDAO.class);
    }
    @Bean
    public IPromotionDAO promotionDAO() {
        return Mockito.mock(IPromotionDAO.class);
    }

    @Bean
    public RegexChecker regexChecker() {
        return Mockito.mock(RegexChecker.class);
    }


}
