package uk.gov.dwp.uc.pairtest.domain;

import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Immutable Object
 */
@Validated
@Getter
public class TicketTypeRequest {

    @Min(value = 0, message = "Number of tickets cannot be negative")
    private int noOfTickets;

    @NotNull(message = "Ticket type should be provided")
    private Type type;

    public TicketTypeRequest(Type type, int noOfTickets) {
        this.type = type;
        this.noOfTickets = noOfTickets;
    }

    public enum Type {
        ADULT, CHILD, INFANT
    }

}
