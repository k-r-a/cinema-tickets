package uk.gov.dwp.uc.pairtest.domain;

import lombok.Getter;

@Getter
public class ValidationStatus {
	private Status status;
	private String message;

	public ValidationStatus(Status status, String message) {
		this.status = status;
		this.message = message;
	}

	public enum Status {
		VALID, INVALID
	}
}