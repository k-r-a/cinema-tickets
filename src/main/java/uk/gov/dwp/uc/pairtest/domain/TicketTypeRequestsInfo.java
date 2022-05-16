package uk.gov.dwp.uc.pairtest.domain;

import lombok.Getter;

@Getter
public class TicketTypeRequestsInfo {
	private int noOfInfantBookings;
	private int noOfChildBookings;
	private int noOfAdultBookings;

	public TicketTypeRequestsInfo(int noOfInfantBookings, int noOfChildBookings, int noOfAdultBookings) {
		this.noOfInfantBookings = noOfInfantBookings;
		this.noOfChildBookings = noOfChildBookings;
		this.noOfAdultBookings = noOfAdultBookings;
	}
}