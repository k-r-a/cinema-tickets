package uk.gov.dwp.uc.pairtest.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class ValidContentsValidator implements ConstraintValidator<ValidContents, TicketTypeRequest[]> {

    @Autowired
    private Validator validator;

    @Override
    public void initialize(ValidContents constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(TicketTypeRequest[] ticketTypeRequests, ConstraintValidatorContext constraintValidatorContext) {
        Set<ConstraintViolation<TicketTypeRequest>> ticketTypeRequestsConstraintViolations = new HashSet<>();
        Arrays.stream(ticketTypeRequests).allMatch(ticketTypeRequest -> ticketTypeRequestsConstraintViolations.addAll(validator.validate(ticketTypeRequest)));
        if (!ticketTypeRequestsConstraintViolations.isEmpty()) {
            return false;
        }
        return true;
    }
}
