package com.sapient.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.sapient.dao.*;

@Configuration
class DataService {

    @Bean
    public AddressDao addressDataService() {
        return new AddressDaoImpl();
    }

    @Bean
    public MessageDao messageDataService() {
        return new MessageDaoImpl();
    }

    @Bean
    public OrderDao orderDataService() {
        return new OrderDaoImpl();
    }

    @Bean
    public ReviewDao reviewDataService() {
        return new ReviewDaoImpl();
    }

    @Bean
    public ServiceDao providerDataService() {
        return new ServiceDaoImpl();
    }

    @Bean
    public UserDao userDataService() {
        return new UserDaoImpl();
    }
}