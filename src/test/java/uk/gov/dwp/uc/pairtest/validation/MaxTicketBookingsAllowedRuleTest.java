package uk.gov.dwp.uc.pairtest.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.ValidationStatus;
import uk.gov.dwp.uc.pairtest.helper.TestsInputsProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MaxTicketBookingsAllowedRuleTest {

    @Autowired
    private MaxTicketBookingsAllowedRule rule;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(rule, "maxTicketsAllowed", "20");
    }

    @Test
    void whenExcessTicketsAreBooked_thenInvalid() {
        TicketTypeRequest[] ticketRequests = TestsInputsProvider.createTicketRequests(10, 10, 15);
        ValidationStatus validationStatus = rule.isValid(ticketRequests);
        assertEquals(validationStatus.getStatus(), ValidationStatus.Status.INVALID);
        assertEquals(validationStatus.getMessage(), "Number of booking requests exceeded maximum limit");
    }

    @ParameterizedTest
    @CsvSource({"2,2,2", "5,5,10"})
    void whenTicketsBookedWithinLimit_thenValid(int noOfInfantBookings, int noOfChildBookings,
                                                int noOfAdultBookings) {
        TicketTypeRequest[] ticketRequests = TestsInputsProvider
                .createTicketRequests(noOfInfantBookings, noOfChildBookings, noOfAdultBookings);
        ValidationStatus validationStatus = rule.isValid(ticketRequests);
        assertEquals(validationStatus.getStatus(), ValidationStatus.Status.VALID);
    }
}

