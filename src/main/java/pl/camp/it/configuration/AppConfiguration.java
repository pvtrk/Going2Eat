package pl.camp.it.configuration;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;
import pl.camp.it.session.SessionObject;

@Configuration
public class AppConfiguration {
    @Bean
    @SessionScope
    SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }

    @Bean
    @SessionScope
    SessionObject sessionObject() {
        return new SessionObject();
    }
}
