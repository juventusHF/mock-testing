package ch.juventus.mocks.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ch.juventus.mocks.model.Booking;

/**
 * Stand-in for a real e-mail service. Only writes to the log.
 */
@Service
public class ConsoleNotificationService implements NotificationService {

	private static final Logger log = LoggerFactory.getLogger(ConsoleNotificationService.class);

	@Override
	public void sendBookingConfirmation(Booking booking) {
		log.info("Mail to {}: your booking {} is confirmed", booking.getCustomerEmail(), booking.getId());
	}

	@Override
	public void sendCancellationConfirmation(Booking booking) {
		log.info("Mail to {}: your booking {} was cancelled", booking.getCustomerEmail(), booking.getId());
	}

}
