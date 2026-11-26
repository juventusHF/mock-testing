package ch.juventus.mocks.exercises;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.juventus.mocks.external.NotificationService;
import ch.juventus.mocks.external.PaymentService;
import ch.juventus.mocks.model.Screening;
import ch.juventus.mocks.repository.BookingRepository;
import ch.juventus.mocks.repository.ScreeningRepository;
import ch.juventus.mocks.service.BookingService;
import ch.juventus.mocks.service.PriceCalculator;

/**
 * Exercise 06: ArgumentCaptor.
 * See exercises/06-argument-captor.md
 */
@ExtendWith(MockitoExtension.class)
class Exercise06Test {

	private static final Screening DUNE_EVENING = new Screening(2, "Dune: Part Two",
			LocalDateTime.of(2026, 12, 5, 20, 45), 80, 18.0);

	@Mock
	private ScreeningRepository screeningRepository;

	@Mock
	private BookingRepository bookingRepository;

	@Mock
	private PaymentService paymentService;

	@Mock
	private NotificationService notificationService;

	// a spy: the real prices are calculated
	@Spy
	private PriceCalculator priceCalculator;

	@InjectMocks
	private BookingService bookingService;

	// TODO 6.0: add the captors you need (Booking, String, Double)

	@Test
	void bookTickets_savesBookingWithCorrectData() {
		fail("TODO 6.1");
	}

	@Test
	void bookTickets_chargesGroupPriceForEveningScreening() {
		fail("TODO 6.2");
	}

	@Test
	void bookTickets_createsUniqueBookingIds() {
		fail("TODO 6.3");
	}

}
