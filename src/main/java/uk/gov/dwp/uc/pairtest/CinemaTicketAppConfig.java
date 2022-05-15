package uk.gov.dwp.uc.pairtest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;

@Configuration
public class CinemaTicketAppConfig {
    @Bean
    public TicketPaymentService getTicketPaymentService() {
        return new TicketPaymentServiceImpl();
    }
}
