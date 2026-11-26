package ch.juventus.mocks.solutions;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.juventus.mocks.external.NotificationService;
import ch.juventus.mocks.external.PaymentService;
import ch.juventus.mocks.model.Booking;
import ch.juventus.mocks.model.Screening;
import ch.juventus.mocks.repository.BookingRepository;
import ch.juventus.mocks.repository.ScreeningRepository;
import ch.juventus.mocks.service.BookingService;
import ch.juventus.mocks.service.PriceCalculator;

@ExtendWith(MockitoExtension.class)
class Exercise02SolutionTest {

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
		when(screeningRepository.findById(1L)).thenReturn(Optional.of(DUNE));
		when(priceCalculator.calculateTotal(DUNE, 2)).thenReturn(36.0);

		Booking booking = bookingService.bookTickets(1L, "anna@example.com", 2);

		verify(paymentService).charge("anna@example.com", 36.0);
		verify(bookingRepository).save(booking);
		verify(notificationService).sendBookingConfirmation(booking);
	}

	@Test
	void bookTickets_doesNothing_whenNumberOfSeatsIsInvalid() {
		assertThrows(IllegalArgumentException.class, () -> bookingService.bookTickets(1L, "anna@example.com", 0));

		verifyNoInteractions(screeningRepository, bookingRepository, paymentService, notificationService);
	}

	@Test
	void bookTickets_loadsScreeningOncePerBooking() {
		when(screeningRepository.findById(1L)).thenReturn(Optional.of(DUNE));

		bookingService.bookTickets(1L, "anna@example.com", 2);
		bookingService.bookTickets(1L, "ben@example.com", 3);

		verify(screeningRepository, times(2)).findById(1L);
	}

	@Test
	void cancelBooking_doesNothing_whenBookingIsAlreadyCancelled() {
		Booking booking = new Booking("b1", 1, "anna@example.com", 2, 36.0, "TX-1");
		booking.cancel();
		when(bookingRepository.findById("b1")).thenReturn(Optional.of(booking));

		assertThrows(IllegalStateException.class, () -> bookingService.cancelBooking("b1"));

		verify(paymentService, never()).refund("TX-1", 36.0);
		verify(bookingRepository, never()).save(booking);
		verifyNoInteractions(notificationService);
	}

}
