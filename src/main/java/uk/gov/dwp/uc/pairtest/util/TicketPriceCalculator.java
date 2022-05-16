package uk.gov.dwp.uc.pairtest.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

@Component
public class TicketPriceCalculator {

	@Value("${infant.ticket.price}")
	private String infantTicketPrice;

	@Value("${child.ticket.price}")
	private String childTicketPrice;

	@Value("${adult.ticket.price}")
	private String adultTicketPrice;

	public int calculateTotalPriceForTickets(int noOfInfantBookings, int noOfChildBookings, int noOfAdultBookings) {
		// Infant price is also calculated to support future changes in infant ticket
		// pricing
		int allInfantsTicketPrice = getTotalPriceForTicketType(TicketTypeRequest.Type.INFANT, noOfInfantBookings);
		int allChildrenTicketPrice = getTotalPriceForTicketType(TicketTypeRequest.Type.CHILD, noOfChildBookings);
		int allAdultTicketPrice = getTotalPriceForTicketType(TicketTypeRequest.Type.ADULT, noOfAdultBookings);

		return allInfantsTicketPrice + allChildrenTicketPrice + allAdultTicketPrice;
	}

	private int getTotalPriceForTicketType(TicketTypeRequest.Type type, int noOfBookings) {
		int totalPriceForTicketType = 0;
		switch (type) {
		case INFANT:
			totalPriceForTicketType = Integer.valueOf(infantTicketPrice) * noOfBookings;
			break;
		case CHILD:
			totalPriceForTicketType = Integer.valueOf(childTicketPrice) * noOfBookings;
			break;
		case ADULT:
			totalPriceForTicketType = Integer.valueOf(adultTicketPrice) * noOfBookings;
			break;
		}
		return totalPriceForTicketType;
	}
}
