package uk.gov.dwp.uc.pairtest.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.ValidationStatus;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class MaxTicketBookingsAllowedRule implements TicketRule {

    String errorMessage = "Number of booking requests exceeded maximum limit";

    @Resource
    @Value("${max.tickets.allowed}")
    private String maxTicketsAllowed;

    @Override
    public ValidationStatus isValid(TicketTypeRequest[] ticketTypeRequests) {

        int totalBookingsRequested = Arrays.stream(ticketTypeRequests).map(ticketTypeRequest -> ticketTypeRequest.getNoOfTickets()).collect(Collectors.summingInt(Integer::intValue));
        if (totalBookingsRequested > Integer.valueOf(maxTicketsAllowed)) {
            return new ValidationStatus(ValidationStatus.Status.INVALID, errorMessage);
        }
        return new ValidationStatus(ValidationStatus.Status.VALID, "");
    }
}
