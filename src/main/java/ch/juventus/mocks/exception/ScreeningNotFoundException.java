package ch.juventus.mocks.exception;

public class ScreeningNotFoundException extends RuntimeException {

	public ScreeningNotFoundException(long screeningId) {
		super("No screening found with id %d".formatted(screeningId));
	}

}
