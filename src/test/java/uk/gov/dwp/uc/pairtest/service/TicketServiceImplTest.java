package uk.gov.dwp.uc.pairtest.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.TicketService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.helper.TestsInputsProvider;

import javax.validation.Validator;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;

@SpringBootTest
public class TicketServiceImplTest {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private Validator validator;

    @Mock
    private TicketPaymentService ticketPaymentService;

    @Mock
    private SeatReservationService mockSeatReservationService;

    @BeforeEach
    public void setUp() {
      /*  ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();*/
    }


    @ParameterizedTest()
    @CsvSource({"-5,1,1,1"})
    @Description("Should throw exception if there is any input validation error")
    void whenInputValidationFails_thenException(Long accountId, int noOfInfantBookings, int noOfChildBookings, int noOfAdultBookings) {
        TicketTypeRequest[] testInputs = TestsInputsProvider.createTicketRequests(noOfInfantBookings, noOfChildBookings, noOfAdultBookings);
        Assertions.assertThrows(InvalidPurchaseException.class, () -> {
            ticketService.purchaseTickets(accountId, testInputs);
        });
    }

    @ParameterizedTest()
    @CsvSource({"2,1,1,0", "2,1,0,0", "2,0,1,0", "2,10,10,10"})
    @Description("Should throw exception if there is any business validation error")
    void whenBusinessValidationFails_thenException(Long accountId, int noOfInfantBookings, int noOfChildBookings, int noOfAdultBookings) {
        TicketTypeRequest[] testInputs = TestsInputsProvider.createTicketRequests(noOfInfantBookings, noOfChildBookings, noOfAdultBookings);
        Assertions.assertThrows(InvalidPurchaseException.class, () -> {
            ticketService.purchaseTickets(accountId, testInputs);
        });
    }

    @ParameterizedTest()
    @CsvSource({"2,1,1,1", "25,0,0,1", "223,0,1,1", "5647,1,0,1"})
    @Description("Tests Happy path")
    void whenNoValidationFailures_thenSuccess(Long accountId, int noOfInfantBookings, int noOfChildBookings, int noOfAdultBookings) {
        TicketTypeRequest[] testInputs = TestsInputsProvider.createTicketRequests(noOfInfantBookings, noOfChildBookings, noOfAdultBookings);

        Mockito.verify(mockSeatReservationService, times(1)).reserveSeat(anyLong(), anyInt());
        Mockito.verify(ticketPaymentService, times(1)).makePayment(anyLong(), anyInt());

    }
}
