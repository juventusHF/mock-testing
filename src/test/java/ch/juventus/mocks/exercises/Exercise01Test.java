package ch.juventus.mocks.exercises;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import ch.juventus.mocks.external.NotificationService;
import ch.juventus.mocks.external.PaymentService;
import ch.juventus.mocks.model.Screening;
import ch.juventus.mocks.repository.BookingRepository;
import ch.juventus.mocks.repository.ScreeningRepository;
import ch.juventus.mocks.service.BookingService;
import ch.juventus.mocks.service.PriceCalculator;
import ch.juventus.mocks.service.ScreeningService;

/**
 * Exercise 01: @Mock, @InjectMocks and when().thenReturn().
 * See exercises/01-mock-and-injectmocks.md
 */
// TODO 1.1: enable the Mockito extension
class Exercise01Test {

	private static final Screening DUNE_AFTERNOON = new Screening(1, "Dune: Part Two",
			LocalDateTime.of(2026, 12, 5, 17, 30), 80, 18.0);
	private static final Screening DUNE_EVENING = new Screening(2, "Dune: Part Two",
			LocalDateTime.of(2026, 12, 5, 20, 45), 80, 18.0);
	private static final Screening INSIDE_OUT = new Screening(3, "Inside Out 2",
			LocalDateTime.of(2026, 12, 6, 14, 0), 120, 15.0);

	// TODO 1.2: turn these fields into mocks
	private ScreeningRepository screeningRepository;

	private BookingRepository bookingRepository;

	private PaymentService paymentService;

	private NotificationService notificationService;

	private PriceCalculator priceCalculator;

	// TODO 1.3: let Mockito create the services and inject the mocks
	private ScreeningService screeningService;

	private BookingService bookingService;

	@Test
	void getScreening_returnsScreeningFromRepository() {
		fail("TODO 1.4");
	}

	@Test
	void getScreening_throwsException_whenScreeningDoesNotExist() {
		fail("TODO 1.5");
	}

	@Test
	void findByTitle_returnsOnlyMatchingScreenings_ignoringCase() {
		fail("TODO 1.6");
	}

	@Test
	void getAvailableSeats_subtractsConfirmedBookings() {
		fail("TODO 1.7");
	}

}
