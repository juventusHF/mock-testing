package ch.juventus.mocks.solutions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.juventus.mocks.exception.NotEnoughSeatsException;
import ch.juventus.mocks.external.NotificationService;
import ch.juventus.mocks.external.PaymentService;
import ch.juventus.mocks.model.Booking;
import ch.juventus.mocks.model.Screening;
import ch.juventus.mocks.repository.InMemoryBookingRepository;
import ch.juventus.mocks.repository.ScreeningRepository;
import ch.juventus.mocks.service.BookingService;
import ch.juventus.mocks.service.PriceCalculator;

@ExtendWith(MockitoExtension.class)
class Exercise04SolutionTest {

	private static final Screening DUNE_AFTERNOON = new Screening(1, "Dune: Part Two",
			LocalDateTime.of(2026, 12, 5, 17, 30), 80, 18.0);
	private static final Screening DUNE_EVENING = new Screening(2, "Dune: Part Two",
			LocalDateTime.of(2026, 12, 5, 20, 45), 80, 18.0);

	@Spy
	private PriceCalculator priceCalculator;

	@Spy
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
		doReturn(10.0).when(priceCalculator).calculateSeatPrice(DUNE_AFTERNOON);

		double total = priceCalculator.calculateTotal(DUNE_AFTERNOON, 5);

		assertEquals(45.0, total);
	}

	@Test
	void whenThenReturn_callsTheRealMethodOnASpy() {
		// when(...) first executes the real method -> NullPointerException because screening is null
		assertThrows(NullPointerException.class,
				() -> when(priceCalculator.calculateSeatPrice(null)).thenReturn(10.0));

		// doReturn(...) does not call the real method
		doReturn(10.0).when(priceCalculator).calculateSeatPrice(null);
		assertEquals(10.0, priceCalculator.calculateSeatPrice(null));
	}

	// --- Part B: spies injected into BookingService ---

	@Test
	void bookTickets_chargesRealPrice_forEveningScreening() {
		when(screeningRepository.findById(2L)).thenReturn(Optional.of(DUNE_EVENING));

		bookingService.bookTickets(2L, "anna@example.com", 2);

		verify(priceCalculator).calculateTotal(DUNE_EVENING, 2);
		verify(paymentService).charge("anna@example.com", 42.0);
	}

	@Test
	void bookTickets_throwsException_whenRepositoryReportsFullScreening() {
		when(screeningRepository.findById(1L)).thenReturn(Optional.of(DUNE_AFTERNOON));
		doReturn(List.of(new Booking("b1", 1, "group@example.com", 80, 1296.0, "TX-1")))
				.when(bookingRepository).findByScreeningId(1L);

		assertThrows(NotEnoughSeatsException.class, () -> bookingService.bookTickets(1L, "anna@example.com", 1));
	}

}
