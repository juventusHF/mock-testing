package ch.juventus.mocks.exception;

public class PaymentFailedException extends RuntimeException {

	public PaymentFailedException(String message) {
		super(message);
	}

}
