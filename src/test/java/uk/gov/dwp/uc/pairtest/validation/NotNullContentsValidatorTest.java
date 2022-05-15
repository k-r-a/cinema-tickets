package uk.gov.dwp.uc.pairtest.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.helper.TestsInputsProvider;

import javax.validation.ConstraintValidatorContext;

@SpringBootTest
public class NotNullContentsValidatorTest {

    @InjectMocks
    NotNullContentsValidator notNullContentsValidator = new NotNullContentsValidator();

    @Mock
    ConstraintValidatorContext constraint;

    @Test
    @Description("When array contains null then validation fails")
    void whenContentsAreNull_thenNotValid() {
        TicketTypeRequest[] nullContentInput = TestsInputsProvider.createTicketRequestsWithNullContent();
        Assertions.assertFalse(notNullContentsValidator.isValid(nullContentInput, constraint));
    }

    @ParameterizedTest()
    @CsvSource({"1,1,1", "0,0,1", "0,1,1", "1,0,1"})
    @Description("Tests Happy path")
    void whenContentsAreCorrect_thenValid(int noOfInfantBookings, int noOfChildBookings, int noOfAdultBookings) {
        TicketTypeRequest[] ticketRequests = TestsInputsProvider.createTicketRequests(noOfInfantBookings, noOfChildBookings, noOfAdultBookings);
        Assertions.assertTrue(notNullContentsValidator.isValid(ticketRequests, constraint));
    }
}
