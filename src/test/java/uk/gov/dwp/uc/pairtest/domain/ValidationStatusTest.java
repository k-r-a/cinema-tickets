package uk.gov.dwp.uc.pairtest.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.dwp.uc.pairtest.domain.ValidationStatus.Status;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ValidationStatusTest {

    @Test
    void test() {
        ValidationStatus validationStatus = new ValidationStatus(Status.VALID, "message");

        assertEquals(Status.VALID, validationStatus.getStatus());
        assertEquals("message", validationStatus.getMessage());
    }

}
