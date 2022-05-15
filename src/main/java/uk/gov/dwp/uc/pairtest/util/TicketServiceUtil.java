package uk.gov.dwp.uc.pairtest.util;

import lombok.extern.slf4j.Slf4j;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequestsInfo;

@Slf4j
public class TicketServiceUtil {

    public static TicketTypeRequestsInfo parseTicketTypeRequests(TicketTypeRequest[] ticketTypeRequests) {
        int noOfInfantBookings = 0;
        int noOfChildBookings = 0;
        int noOfAdultBookings = 0;

        for (TicketTypeRequest ticketRequest : ticketTypeRequests) {
            if (ticketRequest.getTicketType() == TicketTypeRequest.Type.INFANT) {
                noOfInfantBookings = noOfInfantBookings + ticketRequest.getNoOfTickets();
            }
            if (ticketRequest.getTicketType() == TicketTypeRequest.Type.CHILD) {
                noOfChildBookings = noOfChildBookings + ticketRequest.getNoOfTickets();
            }
            if (ticketRequest.getTicketType() == TicketTypeRequest.Type.ADULT) {
                noOfAdultBookings = noOfAdultBookings + ticketRequest.getNoOfTickets();
            }
        }
        return new TicketTypeRequestsInfo(noOfInfantBookings, noOfChildBookings, noOfAdultBookings);
    }
}