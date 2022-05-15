package uk.gov.dwp.uc.pairtest.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TicketTypeRequestsInfoTest {

    @Test
    void testTicketTypeRequestsInfoObjectCreation() {

        TicketTypeRequestsInfo info = new TicketTypeRequestsInfo(1, 2, 3);
        assertEquals(1, info.getNoOfInfantBookings());
        assertEquals(2, info.getNoOfChildBookings());
        assertEquals(3, info.getNoOfAdultBookings());
    }
}
