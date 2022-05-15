package uk.gov.dwp.uc.pairtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.TicketService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;


public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketPaymentService ticketPaymentService;

    @Autowired
    private SeatReservationService seatReservationService;

    /**
     * Should only have private methods other than the one below.
     */
    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {

        //Validate Inputs
        //Validate Business rules
        //Call TicketPaymentService
        //Call SeatReservationService
    }
}
