package uk.gov.dwp.uc.pairtest.validation;

import org.springframework.stereotype.Component;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.ValidationStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoInfantOrChildWithoutAdultRule implements TicketRule {

    @Override
    public ValidationStatus isValid(TicketTypeRequest[] ticketTypeRequests) {
        List<TicketTypeRequest.Type> typeList = Arrays.stream(ticketTypeRequests).filter(ticketTypeRequest -> ticketTypeRequest.getNoOfTickets() > 0).map(ticketTypeRequest -> ticketTypeRequest.getTicketType()).collect(Collectors.toList());
        boolean isAdultBooked = typeList.contains(TicketTypeRequest.Type.ADULT);
        boolean isChildBooked = typeList.contains(TicketTypeRequest.Type.CHILD);
        boolean isInfantBooked = typeList.contains(TicketTypeRequest.Type.INFANT);
        if ((isChildBooked || isInfantBooked) && !isAdultBooked) {
            return new ValidationStatus(ValidationStatus.Status.INVALID, "Infant or child ticket cannot be booked without adult");
        }
        return new ValidationStatus(ValidationStatus.Status.VALID, "Booking is valid");
    }

}
