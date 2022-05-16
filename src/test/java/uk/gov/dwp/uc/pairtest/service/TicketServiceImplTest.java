package uk.gov.dwp.uc.pairtest.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.TicketService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.helper.TestsInputsProvider;
import uk.gov.dwp.uc.pairtest.util.TicketPriceCalculator;
import uk.gov.dwp.uc.pairtest.validation.TicketsRuleValidator;

import java.util.StringJoiner;

import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
class TicketServiceImplTest {

    @Autowired
    TicketService ticketService;

    @Mock
    private SeatReservationService mockSeatReservationService;

    @Mock
    private TicketPaymentService ticketPaymentService;

    @Mock
    private TicketPriceCalculator ticketPriceCalculator;

    @Mock
    private TicketsRuleValidator ticketsRuleValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @ParameterizedTest
    @CsvSource({"1,1,1", "1,0,1", "0,1,1"})
    @Description("Given an valid request user should be able to book ticket")
    void whenNoValidationFailures_thenSuccess(int noOfInfantBookings, int noOfChildBookings, int noOfAdultBookings) {
        TicketTypeRequest[] testInputs = TestsInputsProvider.createTicketRequests(noOfInfantBookings,
                noOfChildBookings, noOfAdultBookings);
        Mockito.doNothing().when(mockSeatReservationService).reserveSeat(anyLong(), anyInt());
        Mockito.doNothing().when(ticketPaymentService).makePayment(anyLong(), anyInt());

        Mockito.when(ticketsRuleValidator.validate(any())).thenReturn(new StringJoiner(","));
        Mockito.when(ticketPriceCalculator.calculateTotalPriceForTickets(anyInt(), anyInt(), anyInt()))
                .thenReturn(30);
        Assertions.assertDoesNotThrow(() -> ticketService.purchaseTickets(2L, testInputs));
    }

    @Test
    @Description("Should Fail if there is a input validation Error")
    void whenInputValidationFails_thenException() {
        TicketTypeRequest[] testInputs = TestsInputsProvider.createTicketRequests(1,
                1, 1);
        Mockito.when(ticketsRuleValidator.validate(testInputs))
                .thenReturn(new StringJoiner(","));
        Assertions.assertThrows(InvalidPurchaseException.class, () -> {
            ticketService.purchaseTickets(-5L, testInputs);
        });
    }

    @ParameterizedTest()
    @CsvSource({"1,1,0", "1,0,0", "0,1,0", "10,10,10"})
    @Description("Should Fail if there is a rule validation Error")
    void whenBusinessValidationFails_thenException(int noOfInfantBookings, int noOfChildBookings,
                                                   int noOfAdultBookings) {
        TicketTypeRequest[] testInputs = TestsInputsProvider.createTicketRequests(noOfInfantBookings,
                noOfChildBookings, noOfAdultBookings);
        Mockito.when(ticketsRuleValidator.validate(testInputs))
                .thenReturn(new StringJoiner(",").add("Some invalid rule message"));
        Assertions.assertThrows(InvalidPurchaseException.class, () -> {
            ticketService.purchaseTickets(2L, testInputs);
        });
    }
}
