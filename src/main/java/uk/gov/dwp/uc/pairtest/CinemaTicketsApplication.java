package uk.gov.dwp.uc.pairtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = {"thirdparty", "uk.gov.dwp.uc.pairtest"})
public class CinemaTicketsApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(CinemaTicketsApplication.class, args);


    }

}
