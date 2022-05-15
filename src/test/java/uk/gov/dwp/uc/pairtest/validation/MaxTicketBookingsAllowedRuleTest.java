package uk.gov.dwp.uc.pairtest.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.ValidationStatus;
import uk.gov.dwp.uc.pairtest.helper.TestsInputsProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MaxTicketBookingsAllowedRuleTest {

    @InjectMocks
    private MaxTicketBookingsAllowedRule rule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(rule, "maxTicketsAllowed", "20");
    }

    @Test
    void whenExcessTicketsAreBooked_thenInvalid() {
        TicketTypeRequest[] ticketRequests = TestsInputsProvider.createTicketRequests(10, 10, 15);
        ValidationStatus validationStatus = rule.isValid(ticketRequests);
        assertEquals(validationStatus.getStatus(), ValidationStatus.Status.INVALID);
        assertEquals(validationStatus.getMessage(), "Number of booking requests exceeded maximum limit");
    }

    @Test
    void whenTicketsBookedWithinLimit_thenValid() {
        TicketTypeRequest[] ticketRequests = TestsInputsProvider.createTicketRequests(2, 2, 2);
        ValidationStatus validationStatus = rule.isValid(ticketRequests);
        assertEquals(validationStatus.getStatus(), ValidationStatus.Status.VALID);
    }

    @Test
    void whenTicketsBookedSameAsLimit_thenValid() {
        TicketTypeRequest[] ticketRequests = TestsInputsProvider.createTicketRequests(5, 5, 10);
        ValidationStatus validationStatus = rule.isValid(ticketRequests);
        assertEquals(validationStatus.getStatus(), ValidationStatus.Status.VALID);
    }
}

