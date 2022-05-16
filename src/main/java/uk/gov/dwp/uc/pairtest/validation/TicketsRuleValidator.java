package uk.gov.dwp.uc.pairtest.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.ValidationStatus;

import java.util.List;
import java.util.StringJoiner;

@Component
@Validated
public class TicketsRuleValidator {

	private List<TicketRule> rules;

	@Autowired
	public TicketsRuleValidator(List<TicketRule> ticketRules) {
		this.rules = ticketRules;
	}

	public StringJoiner validate(TicketTypeRequest[] ticketRequests) {

		StringJoiner errors = new StringJoiner(",");
		for (TicketRule rule : rules) {
			ValidationStatus validationStatus = rule.isValid(ticketRequests);
			if (ValidationStatus.Status.INVALID.equals(validationStatus.getStatus())) {
				errors.add(validationStatus.getMessage());
			}
		}
		return errors;
	}
}