package uk.gov.dwp.uc.pairtest.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TicketTypeRequestTest {

	@Autowired
	private Validator validator;

	@Test
	void whenAllArgumentsPassed_thenObjectIsCreated() {
		TicketTypeRequest ticketTypeRequest = new TicketTypeRequest(Type.ADULT, 2);
		assertNotNull(ticketTypeRequest);
	}

	@Test
	void whenAllValidArgumentsPassed_thenExceptNoViolations() {
		TicketTypeRequest ticketTypeRequest = new TicketTypeRequest(Type.ADULT, 2);
		Set<ConstraintViolation<TicketTypeRequest>> violations = validator.validate(ticketTypeRequest);
		assertEquals(0, violations.size());
	}

	@Test
	void whenTicketTypeIsNull_thenExpectViolation() {
		TicketTypeRequest ticketTypeRequest = new TicketTypeRequest(null, 2);

		Set<ConstraintViolation<TicketTypeRequest>> violations = validator.validate(ticketTypeRequest);
		assertEquals(1, violations.size());

		ConstraintViolation<TicketTypeRequest> violation = violations.iterator().next();
		assertEquals("Ticket type should be provided", violation.getMessage());
	}

	@Test
	void whenNoOfTicketIsNegative_thenExpectViolation() {
		TicketTypeRequest ticketTypeRequest = new TicketTypeRequest(Type.ADULT, -2);

		Set<ConstraintViolation<TicketTypeRequest>> violations = validator.validate(ticketTypeRequest);
		assertEquals(1, violations.size());

		ConstraintViolation<TicketTypeRequest> violation = violations.iterator().next();
		assertEquals("Number of tickets cannot be negative", violation.getMessage());
	}
}
