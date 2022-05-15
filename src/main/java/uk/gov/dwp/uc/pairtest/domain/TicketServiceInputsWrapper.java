package uk.gov.dwp.uc.pairtest.domain;

import lombok.Getter;
import org.springframework.validation.annotation.Validated;
import uk.gov.dwp.uc.pairtest.validation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Validated
public class TicketServiceInputsWrapper {

    @NotNull(groups = BasicValidationGroup.class, message = "AccountId must be provided")
    @Min(groups = BasicValidationGroup.class, value = 1, message = "AccountId is invalid")
    private Long accountId;

    @NotEmpty(groups = BasicValidationGroup.class, message = "Ticket type requests must be provided")
    @NotNullContents(groups = IntermediateValidationGroup.class, message = "Ticket Type Request cannot be null")
    @ValidContents(groups = AdvanceValidationGroup.class, message = "Ticket Type Request is not valid")
    private TicketTypeRequest[] ticketTypeRequests;

    public TicketServiceInputsWrapper(Long accountId, TicketTypeRequest[] ticketTypeRequests) {
        this.accountId = accountId;
        this.ticketTypeRequests = ticketTypeRequests;
    }
}
