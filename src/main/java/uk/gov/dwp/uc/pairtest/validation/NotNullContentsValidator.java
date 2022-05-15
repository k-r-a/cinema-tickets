package uk.gov.dwp.uc.pairtest.validation;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class NotNullContentsValidator implements ConstraintValidator<NotNullContents, TicketTypeRequest[]> {

    @Override
    public void initialize(NotNullContents notNullContents) {
    }

    @Override
    public boolean isValid(TicketTypeRequest[] ticketRequests, ConstraintValidatorContext context) {
        return Arrays.stream(ticketRequests).allMatch(ticketTypeRequest -> ticketTypeRequest != null);
    }
}
