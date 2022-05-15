package uk.gov.dwp.uc.pairtest.service;

import org.springframework.stereotype.Service;
import thirdparty.seatbooking.SeatReservationService;

@Service
public class SeatReservationServiceImpl implements SeatReservationService {
    @Override
    public void reserveSeat(long accountId, int totalSeatsToAllocate) {
        // Real implementation omitted
    }
}
