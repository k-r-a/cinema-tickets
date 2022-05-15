package uk.gov.dwp.uc.pairtest.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.helper.TestsInputsProvider;
import uk.gov.dwp.uc.pairtest.validation.AdvanceValidationGroup;
import uk.gov.dwp.uc.pairtest.validation.BasicValidationGroup;
import uk.gov.dwp.uc.pairtest.validation.IntermediateValidationGroup;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TicketServiceInputsWrapperTest {

    @Autowired
    private Validator validator;

    @Test
    void whenAllArgumentsPassed_thenObjectIsCreated() {
        TicketTypeRequest[] ticketTypeRequests = TestsInputsProvider.createTicketRequests(1, 1, 1);
        TicketServiceInputsWrapper wrapper = new TicketServiceInputsWrapper(1L, ticketTypeRequests);

        assertEquals(1L, wrapper.getAccountId());
        assertNotNull(wrapper.getTicketTypeRequests());
    }

    @Test
    void whenAllValidArgumentsPassed_thenExpectNoViolations() {
        TicketTypeRequest[] ticketTypeRequests = TestsInputsProvider.createTicketRequests(1, 1, 1);
        TicketServiceInputsWrapper wrapper = new TicketServiceInputsWrapper(1L, ticketTypeRequests);

        Set<ConstraintViolation<TicketServiceInputsWrapper>> violations = validator.validate(wrapper);
        assertEquals(0, violations.size());
    }

    @Test
    void whenTicketTypeArgumentIsNull_thenObjectIsCreatedWithNullType() {
        TicketServiceInputsWrapper wrapper = new TicketServiceInputsWrapper(1L, null);

        assertEquals(1L, wrapper.getAccountId());
        assertNull(wrapper.getTicketTypeRequests());
    }

    @Test
    void whenAccountIdArgumentIsNull_thenExpectViolations() {
        TicketTypeRequest[] ticketTypeRequests = TestsInputsProvider.createTicketRequests(1, 1, 1);
        TicketServiceInputsWrapper wrapper = new TicketServiceInputsWrapper(-2L, ticketTypeRequests);

        Set<ConstraintViolation<TicketServiceInputsWrapper>> violations = validator.validate(wrapper,
                BasicValidationGroup.class);

        ConstraintViolation<TicketServiceInputsWrapper> violation = violations.iterator().next();

        assertEquals("AccountId is invalid", violation.getMessage());

    }

    @Test
    void whenAccountIdIsNullArgumentIsNull_thenExpectViolations() {

        TicketTypeRequest adultTicket = new TicketTypeRequest(Type.ADULT, 1);
        TicketTypeRequest childTicket = new TicketTypeRequest(Type.CHILD, 1);
        TicketTypeRequest infantTicket = new TicketTypeRequest(Type.INFANT, 1);

        TicketTypeRequest[] ticketTypeRequests = {adultTicket, childTicket, infantTicket};

        TicketServiceInputsWrapper wrapper = new TicketServiceInputsWrapper(null, ticketTypeRequests);

        Set<ConstraintViolation<TicketServiceInputsWrapper>> violations = validator.validate(wrapper,
                BasicValidationGroup.class);

        ConstraintViolation<TicketServiceInputsWrapper> violation = violations.iterator().next();

        assertEquals("AccountId must be provided", violation.getMessage());

    }

    @Test
    void whenTicketTypeIsEmptyArgumentIsNull_thenExpectViolations() {

        TicketServiceInputsWrapper wrapper = new TicketServiceInputsWrapper(2L, new TicketTypeRequest[0]);

        Set<ConstraintViolation<TicketServiceInputsWrapper>> violations = validator.validate(wrapper,
                BasicValidationGroup.class);

        ConstraintViolation<TicketServiceInputsWrapper> violation = violations.iterator().next();

        assertEquals("Ticket type requests must be provided", violation.getMessage());

    }

    @Test
    void whenTicketTypeElementIsNull_thenExpectViolations() {

        TicketServiceInputsWrapper wrapper = new TicketServiceInputsWrapper(2L, new TicketTypeRequest[]{null});

        Set<ConstraintViolation<TicketServiceInputsWrapper>> violations = validator.validate(wrapper,
                IntermediateValidationGroup.class);

        ConstraintViolation<TicketServiceInputsWrapper> violation = violations.iterator().next();

        assertEquals("Ticket Type Request cannot be null", violation.getMessage());

    }

    @Test
    void whenTicketTypeElementsValuesAreIncorrect_thenExpectViolations() {

        TicketServiceInputsWrapper wrapper = new TicketServiceInputsWrapper(2L,
                new TicketTypeRequest[]{new TicketTypeRequest(Type.ADULT, -1)});

        Set<ConstraintViolation<TicketServiceInputsWrapper>> violations = validator.validate(wrapper,
                AdvanceValidationGroup.class);

        ConstraintViolation<TicketServiceInputsWrapper> violation = violations.iterator().next();

        assertEquals("Ticket Type Request is not valid", violation.getMessage());

    }

}
