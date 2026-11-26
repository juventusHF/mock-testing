package ch.juventus.mocks.exception;

public class BookingNotFoundException extends RuntimeException {

	public BookingNotFoundException(String bookingId) {
		super("No booking found with id %s".formatted(bookingId));
	}

}
