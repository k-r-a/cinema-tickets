package uk.gov.dwp.uc.pairtest.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequestsInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TicketServiceUtilTest {

	@Test
	void whenMultipleTicketTypeRequestsPassed_ThenGetNumberOfBookingForEachType() {

		TicketTypeRequest infantTicket = new TicketTypeRequest(Type.INFANT, 1);
		TicketTypeRequest childTicket = new TicketTypeRequest(Type.CHILD, 2);
		TicketTypeRequest adultTicket = new TicketTypeRequest(Type.ADULT, 1);

		TicketTypeRequest anotherInfant = new TicketTypeRequest(Type.INFANT, 0);
		TicketTypeRequest anotherChild = new TicketTypeRequest(Type.CHILD, 2);
		TicketTypeRequest anotherAdult = new TicketTypeRequest(Type.ADULT, 2);

		TicketTypeRequest[] ticketTypeRequests = { adultTicket, anotherAdult, childTicket, anotherChild, infantTicket,
				anotherInfant };

		TicketTypeRequestsInfo info = TicketServiceUtil.parseTicketTypeRequests(ticketTypeRequests);

		assertEquals(3, info.getNoOfAdultBookings());
		assertEquals(4, info.getNoOfChildBookings());
		assertEquals(1, info.getNoOfInfantBookings());
	}
}
