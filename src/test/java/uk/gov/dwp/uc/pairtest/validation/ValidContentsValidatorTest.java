package uk.gov.dwp.uc.pairtest.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.helper.TestsInputsProvider;

@SpringBootTest
class ValidContentsValidatorTest {

    @Autowired
    private ValidContentsValidator validContentsValidator;

    @Test
    @Description("When array has invalid contents then validation fails")
    void whenContentsAreInvalid_thenNotValid() {
        TicketTypeRequest[] invalidContentInput = TestsInputsProvider.createTicketRequestsWithInvalidContent();

        Assertions.assertFalse(validContentsValidator.isValid(invalidContentInput, null));
    }

    @ParameterizedTest()
    @CsvSource({"1,1,1", "0,0,1", "0,1,1", "1,0,1"})
    @Description("Tests Happy path")
    void whenContentsAreValid_thenValid(int noOfInfantBookings, int noOfChildBookings, int noOfAdultBookings) {
        TicketTypeRequest[] ticketRequests = TestsInputsProvider.createTicketRequests(noOfInfantBookings, noOfChildBookings, noOfAdultBookings);
        Assertions.assertTrue(validContentsValidator.isValid(ticketRequests, null));
    }
}
