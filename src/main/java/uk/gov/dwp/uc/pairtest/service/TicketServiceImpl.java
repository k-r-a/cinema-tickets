package uk.gov.dwp.uc.pairtest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.TicketService;
import uk.gov.dwp.uc.pairtest.domain.TicketServiceInputsWrapper;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequestsInfo;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.util.TicketServiceUtil;
import uk.gov.dwp.uc.pairtest.validation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.StringJoiner;

@Service
@Slf4j
public class TicketServiceImpl implements TicketService {

    @Autowired
    private Validator validator;

    @Autowired
    private TicketsRuleValidator ticketsRuleValidator;

    @Autowired
    private TicketPaymentService ticketPaymentService;

    @Autowired
    private SeatReservationService seatReservationService;

    /**
     * Should only have private methods other than the one below.
     */
    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {

        performInputValidation(accountId, ticketTypeRequests);
        performBusinessValidation(ticketTypeRequests);

        TicketTypeRequestsInfo ticketsInfo = TicketServiceUtil.parseTicketTypeRequests(ticketTypeRequests);
        int noOfInfantBookings = ticketsInfo.getNoOfInfantBookings();
        int noOfChildBookings = ticketsInfo.getNoOfChildBookings();
        int noOfAdultBookings = ticketsInfo.getNoOfAdultBookings();

        //Call TicketPaymentService
        //Call SeatReservationService
    }

    private void performInputValidation(Long accountId, TicketTypeRequest[] ticketTypeRequests) {
        TicketServiceInputsWrapper ticketServiceInputsWrapper = new TicketServiceInputsWrapper(accountId, ticketTypeRequests);
        //If 1 validation group fails, then other groups are not validated
        validateConstraintsAtGivenValidationGroup(ticketServiceInputsWrapper, BasicValidationGroup.class);
        validateConstraintsAtGivenValidationGroup(ticketServiceInputsWrapper, IntermediateValidationGroup.class);
        validateConstraintsAtGivenValidationGroup(ticketServiceInputsWrapper, AdvanceValidationGroup.class);
    }

    private void validateConstraintsAtGivenValidationGroup(TicketServiceInputsWrapper ticketServiceInputsWrapper, Class<? extends ValidationGroup> validationLevel) {
        Set<ConstraintViolation<TicketServiceInputsWrapper>> inputsWrapperBasicConstraintViolations = validator.validate(ticketServiceInputsWrapper, validationLevel);
        if (!inputsWrapperBasicConstraintViolations.isEmpty()) {
            StringJoiner sb = new StringJoiner(",");
            for (ConstraintViolation<TicketServiceInputsWrapper> constraintViolation : inputsWrapperBasicConstraintViolations) {
                sb.add(constraintViolation.getMessage());
            }
            log.error(inputsWrapperBasicConstraintViolations.toString());
            throw new InvalidPurchaseException("Constraint Violations Error occurred: " + sb.toString());
        }
    }

    private void performBusinessValidation(TicketTypeRequest[] ticketTypeRequests) {
        StringJoiner validationErrors = ticketsRuleValidator.validate(ticketTypeRequests);

        if (validationErrors.length() > 1) {
            throw new InvalidPurchaseException(validationErrors.toString());
        }
    }
}
