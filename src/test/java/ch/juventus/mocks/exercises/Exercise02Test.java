package ch.juventus.mocks.exercises;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.juventus.mocks.external.NotificationService;
import ch.juventus.mocks.external.PaymentService;
import ch.juventus.mocks.model.Screening;
import ch.juventus.mocks.repository.BookingRepository;
import ch.juventus.mocks.repository.ScreeningRepository;
import ch.juventus.mocks.service.BookingService;
import ch.juventus.mocks.service.PriceCalculator;

/**
 * Exercise 02: verify().
 * See exercises/02-verify.md
 */
@ExtendWith(MockitoExtension.class)
class Exercise02Test {

	private static final Screening DUNE = new Screening(1, "Dune: Part Two",
			LocalDateTime.of(2026, 12, 5, 17, 30), 80, 18.0);

	@Mock
	private ScreeningRepository screeningRepository;

	@Mock
	private BookingRepository bookingRepository;

	@Mock
	private PaymentService paymentService;

	@Mock
	private NotificationService notificationService;

	@Mock
	private PriceCalculator priceCalculator;

	@InjectMocks
	private BookingService bookingService;

	@Test
	void bookTickets_chargesSavesAndSendsConfirmation() {
		fail("TODO 2.1");
	}

	@Test
	void bookTickets_doesNothing_whenNumberOfSeatsIsInvalid() {
		fail("TODO 2.2");
	}

	@Test
	void bookTickets_loadsScreeningOncePerBooking() {
		fail("TODO 2.3");
	}

	@Test
	void cancelBooking_doesNothing_whenBookingIsAlreadyCancelled() {
		fail("TODO 2.4");
	}

}
