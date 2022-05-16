package uk.gov.dwp.uc.pairtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "thirdparty", "uk.gov.dwp.uc.pairtest" })
public class CinemaTicketsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaTicketsApplication.class, args);
	}
}
