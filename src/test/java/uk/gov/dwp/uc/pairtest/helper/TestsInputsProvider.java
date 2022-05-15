package uk.gov.dwp.uc.pairtest.helper;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import java.util.ArrayList;
import java.util.List;

public class TestsInputsProvider {

    public static TicketTypeRequest[] createTicketRequests(int noOfInfantBookings, int noOfChildBookings, int noOfAdultBookings) {
        List<TicketTypeRequest> list = new ArrayList<TicketTypeRequest>();
        if (noOfInfantBookings > 0) {
            TicketTypeRequest infantTickets = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, noOfInfantBookings);
            list.add(infantTickets);
        }
        if (noOfChildBookings > 0) {
            TicketTypeRequest childTickets = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, noOfChildBookings);
            list.add(childTickets);
        }
        if (noOfAdultBookings > 0) {
            TicketTypeRequest adultTickets = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, noOfAdultBookings);
            list.add(adultTickets);
        }
        TicketTypeRequest[] ticketRequests = list.toArray(new TicketTypeRequest[0]);
        return ticketRequests;
    }

    public static TicketTypeRequest[] createTicketRequestsWithNullContent() {
        TicketTypeRequest[] ticketRequests = new TicketTypeRequest[1];
        ticketRequests[0] = null;
        return ticketRequests;
    }

    public static TicketTypeRequest[] createTicketRequestsWithInvalidContent() {
        TicketTypeRequest[] ticketRequests = new TicketTypeRequest[1];
        ticketRequests[0] = new TicketTypeRequest(null, -2);
        return ticketRequests;
    }
}
