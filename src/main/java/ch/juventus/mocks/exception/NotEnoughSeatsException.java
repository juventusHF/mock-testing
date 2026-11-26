package ch.juventus.mocks.exception;

public class NotEnoughSeatsException extends RuntimeException {

	public NotEnoughSeatsException(int requested, int available) {
		super("Requested %d seats, but only %d are available".formatted(requested, available));
	}

}
