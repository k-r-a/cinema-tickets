package uk.gov.dwp.uc.pairtest.domain;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Immutable Object
 */
@Validated
public class TicketTypeRequest {

    @Min(value = 0, message = "Number of tickets cannot be negative")
    private int noOfTickets;

    @NotNull(message = "Ticket type should be provided")
    private Type type;

    public TicketTypeRequest(Type type, int noOfTickets) {
        this.type = type;
        this.noOfTickets = noOfTickets;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public Type getTicketType() {
        return type;
    }

    public enum Type {
        ADULT, CHILD, INFANT
    }

}
