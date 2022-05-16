package uk.gov.dwp.uc.pairtest.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.helper.TestsInputsProvider;

@SpringBootTest
class TicketsRuleValidatorTest {

    @Autowired
    private TicketsRuleValidator ticketsRuleValidator;

    @ParameterizedTest
    @CsvSource({"1,0,0", "0,1,0", "5,10,10"})
    void whenRuleViolationOccurs_thenInvalid(int noOfInfantBookings, int noOfChildBookings, int noOfAdultBookings) {
        TicketTypeRequest[] ticketRequests = TestsInputsProvider.createTicketRequests(noOfInfantBookings, noOfChildBookings, noOfAdultBookings);
        Assertions.assertTrue((ticketsRuleValidator.validate(ticketRequests)).length() > 1);
    }

    @Test
    void whenNoRuleViolations_thenValid() {
        TicketTypeRequest[] ticketRequests = TestsInputsProvider.createTicketRequests(1, 1, 1);
        Assertions.assertTrue((ticketsRuleValidator.validate(ticketRequests)).length() == 0);
    }


}
