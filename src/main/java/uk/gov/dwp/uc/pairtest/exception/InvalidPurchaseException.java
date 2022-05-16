package uk.gov.dwp.uc.pairtest.exception;

public class InvalidPurchaseException extends RuntimeException {

	private static final long serialVersionUID = 3416669818017631305L;

	public InvalidPurchaseException(String message) {
		super(message);
	}
}
