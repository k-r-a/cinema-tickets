package uk.gov.dwp.uc.pairtest.validation;

import org.springframework.stereotype.Component;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

@Component
public class NotNullContentsValidator implements ConstraintValidator<NotNullContents, TicketTypeRequest[]> {

    @Override
    public boolean isValid(TicketTypeRequest[] ticketRequests, ConstraintValidatorContext context) {
        return Arrays.stream(ticketRequests).allMatch(ticketTypeRequest -> ticketTypeRequest != null);
    }
}
