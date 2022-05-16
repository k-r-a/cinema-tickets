package uk.gov.dwp.uc.pairtest.validation;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.ValidationStatus;

@FunctionalInterface
public interface TicketRule {
	ValidationStatus isValid(TicketTypeRequest[] ticketTypeRequests);
}
