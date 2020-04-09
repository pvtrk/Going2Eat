package pl.camp.it.configuration;

import org.hibernate.SessionFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.context.annotation.SessionScope;
import pl.camp.it.filters.AdminFilter;
import pl.camp.it.filters.RestorerFilter;
import pl.camp.it.filters.UserFilter;
import pl.camp.it.session.SessionObject;

@Configuration
@ImportResource("classpath:applicationContext.xml")
public class AppConfiguration {
    @Bean
    SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }

    @Bean
    @SessionScope
    SessionObject sessionObject() {
        return new SessionObject();
    }

    @Bean
    public FilterRegistrationBean<RestorerFilter> restorerRoleFilter() {
        FilterRegistrationBean<RestorerFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RestorerFilter());
        registrationBean.addUrlPatterns("/restorerMain/*", "/restorerReservations/*",
                "/restorerAllRestaurants/*" , "/addRestaurant/*" , "/addPromotion/*" ,
                "/blockReservations/*" , "/unblockReservations/*" , "/myRestaurants/*" , "/restorerMoreInfo/*",
                "/accept/*", "/decline/*", "/block/*", "/unblock/*");

        return registrationBean;
    }

    @Bean
    FilterRegistrationBean<UserFilter> userRoleFilter() {
        FilterRegistrationBean<UserFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new UserFilter());
        registrationBean.addUrlPatterns("/main", "/", "/index" , "/restaurants", "/promotions/*",
                "/myFavourite/*" , "/myReservations/*", "/addToFavourite/*" , "/makeReservation/*" , "/moreInfo/*");

        return registrationBean;
    }

    @Bean
    FilterRegistrationBean<AdminFilter> adminRoleFilter() {
        FilterRegistrationBean<AdminFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AdminFilter());
        registrationBean.addUrlPatterns("/adminMenu/*" , "/adminRestaurants/*"
                , "/adminReservations/*", "/showUser/*" ,
                "/unblock/*" , "/admin*/" , "/users/*" ,
                "/userRestaurants/*", "/blockRestaurant/*" , "/unblockRestaurant/*");

        return registrationBean;
    }

}
