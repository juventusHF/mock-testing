package ch.juventus.mocks.solutions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.juventus.mocks.exception.ScreeningNotFoundException;
import ch.juventus.mocks.external.NotificationService;
import ch.juventus.mocks.external.PaymentService;
import ch.juventus.mocks.model.Booking;
import ch.juventus.mocks.model.Screening;
import ch.juventus.mocks.repository.BookingRepository;
import ch.juventus.mocks.repository.ScreeningRepository;
import ch.juventus.mocks.service.BookingService;
import ch.juventus.mocks.service.PriceCalculator;
import ch.juventus.mocks.service.ScreeningService;

@ExtendWith(MockitoExtension.class)
class Exercise01SolutionTest {

	private static final Screening DUNE_AFTERNOON = new Screening(1, "Dune: Part Two",
			LocalDateTime.of(2026, 12, 5, 17, 30), 80, 18.0);
	private static final Screening DUNE_EVENING = new Screening(2, "Dune: Part Two",
			LocalDateTime.of(2026, 12, 5, 20, 45), 80, 18.0);
	private static final Screening INSIDE_OUT = new Screening(3, "Inside Out 2",
			LocalDateTime.of(2026, 12, 6, 14, 0), 120, 15.0);

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
	private ScreeningService screeningService;

	@InjectMocks
	private BookingService bookingService;

	@Test
	void getScreening_returnsScreeningFromRepository() {
		when(screeningRepository.findById(1L)).thenReturn(Optional.of(DUNE_AFTERNOON));

		Screening result = screeningService.getScreening(1L);

		assertSame(DUNE_AFTERNOON, result);
	}

	@Test
	void getScreening_throwsException_whenScreeningDoesNotExist() {
		when(screeningRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(ScreeningNotFoundException.class, () -> screeningService.getScreening(99L));
	}

	@Test
	void findByTitle_returnsOnlyMatchingScreenings_ignoringCase() {
		when(screeningRepository.findAll()).thenReturn(List.of(DUNE_AFTERNOON, INSIDE_OUT, DUNE_EVENING));

		List<Screening> result = screeningService.findByTitle("dune");

		assertEquals(2, result.size());
		assertTrue(result.contains(DUNE_AFTERNOON));
		assertTrue(result.contains(DUNE_EVENING));
	}

	@Test
	void getAvailableSeats_subtractsConfirmedBookings() {
		when(screeningRepository.findById(1L)).thenReturn(Optional.of(DUNE_AFTERNOON));
		when(bookingRepository.findByScreeningId(1L)).thenReturn(List.of(
				new Booking("b1", 1, "anna@example.com", 3, 54.0, "TX-1"),
				new Booking("b2", 1, "ben@example.com", 4, 72.0, "TX-2")));

		int result = bookingService.getAvailableSeats(1L);

		assertEquals(73, result);
	}

}
