package uk.gov.dwp.uc.pairtest.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.ValidationStatus;
import uk.gov.dwp.uc.pairtest.helper.TestsInputsProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class NoInfantOrChildWithoutAdultRuleTest {

    @InjectMocks
    private NoInfantOrChildWithoutAdultRule rule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvSource({"1, 0, 0", "0, 1, 0"})
    void whenInfantOrChildBookedWithoutAdult_thenInvalid(int noOfInfantBookings, int noOfChildBookings,
                                                         int noOfAdultBookings) {
        TicketTypeRequest[] ticketRequests = TestsInputsProvider.createTicketRequests(noOfInfantBookings, noOfChildBookings, noOfAdultBookings);
        ValidationStatus validationStatus = rule.isValid(ticketRequests);
        assertEquals(validationStatus.getStatus(), ValidationStatus.Status.INVALID);
        assertEquals(validationStatus.getMessage(), "Infant or child ticket cannot be booked without adult");
    }

    @ParameterizedTest
    @CsvSource({"1, 0, 1", "0, 1, 1", "0, 0, 1"})
    void whenAdultBookedWithOrWithoutDependants_thenValid(int noOfInfantBookings, int noOfChildBookings,
                                                          int noOfAdultBookings) {
        TicketTypeRequest[] ticketRequests = TestsInputsProvider.createTicketRequests(noOfInfantBookings, noOfChildBookings, noOfAdultBookings);
        ValidationStatus validationStatus = rule.isValid(ticketRequests);
        assertEquals(validationStatus.getStatus(), ValidationStatus.Status.VALID);
    }
}
