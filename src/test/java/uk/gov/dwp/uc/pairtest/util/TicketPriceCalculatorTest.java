package uk.gov.dwp.uc.pairtest.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
public class TicketPriceCalculatorTest {

    @InjectMocks
    TicketPriceCalculator ticketPriceCalculator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(ticketPriceCalculator, "infantTicketPrice", "0");
        ReflectionTestUtils.setField(ticketPriceCalculator, "childTicketPrice", "10");
        ReflectionTestUtils.setField(ticketPriceCalculator, "adultTicketPrice", "20");
    }

    @ParameterizedTest()
    @CsvSource({"2,2,5,120", "1,0,1,20"})
    void whenCalculatePrice_thenReturnsCorrectAmount(int noOfInfantBookings, int noOfChildBookings, int noOfAdultBookings, int expectedTotal) {
        int totalTicketsPrice = ticketPriceCalculator.calculateTotalPriceForTickets(noOfInfantBookings, noOfChildBookings, noOfAdultBookings);
        Assertions.assertEquals(expectedTotal, totalTicketsPrice);
    }
}
