package uk.gov.dwp.uc.pairtest.domain;

import lombok.Getter;

@Getter
public class TicketTypeRequestsInfo {
    public int noOfInfantBookings = 0;
    public int noOfChildBookings = 0;
    public int noOfAdultBookings = 0;

    public TicketTypeRequestsInfo(int noOfInfantBookings, int noOfChildBookings, int noOfAdultBookings) {
        this.noOfInfantBookings = noOfInfantBookings;
        this.noOfChildBookings = noOfChildBookings;
        this.noOfAdultBookings = noOfAdultBookings;
    }

}