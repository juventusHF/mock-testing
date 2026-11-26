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
import ch.juventus.mocks.repository.InMemoryBookingRepository;
import ch.juventus.mocks.repository.ScreeningRepository;
import ch.juventus.mocks.service.BookingService;
import ch.juventus.mocks.service.PriceCalculator;

/**
 * Exercise 04: @Spy and doReturn().when().
 * See exercises/04-spy.md
 */
@ExtendWith(MockitoExtension.class)
class Exercise04Test {

	private static final Screening DUNE_AFTERNOON = new Screening(1, "Dune: Part Two",
			LocalDateTime.of(2026, 12, 5, 17, 30), 80, 18.0);
	private static final Screening DUNE_EVENING = new Screening(2, "Dune: Part Two",
			LocalDateTime.of(2026, 12, 5, 20, 45), 80, 18.0);

	// TODO 4.0: turn these two fields into spies
	private PriceCalculator priceCalculator;

	private InMemoryBookingRepository bookingRepository;

	@Mock
	private ScreeningRepository screeningRepository;

	@Mock
	private PaymentService paymentService;

	@Mock
	private NotificationService notificationService;

	@InjectMocks
	private BookingService bookingService;

	// --- Part A: spy on PriceCalculator ---

	@Test
	void calculateTotal_appliesGroupDiscount_withStubbedSeatPrice() {
		fail("TODO 4.1");
	}

	@Test
	void whenThenReturn_callsTheRealMethodOnASpy() {
		fail("TODO 4.2");
	}

	// --- Part B: spies injected into BookingService ---

	@Test
	void bookTickets_chargesRealPrice_forEveningScreening() {
		fail("TODO 4.3");
	}

	@Test
	void bookTickets_throwsException_whenRepositoryReportsFullScreening() {
		fail("TODO 4.4");
	}

}
