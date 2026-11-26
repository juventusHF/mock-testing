package ch.juventus.mocks.external;

import ch.juventus.mocks.model.Booking;

/**
 * Sends e-mails to customers.
 */
public interface NotificationService {

	void sendBookingConfirmation(Booking booking);

	void sendCancellationConfirmation(Booking booking);

}
