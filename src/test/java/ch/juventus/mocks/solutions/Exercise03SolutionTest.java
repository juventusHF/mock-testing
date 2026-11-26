package ch.juventus.mocks.solutions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
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

import ch.juventus.mocks.exception.PaymentFailedException;
import ch.juventus.mocks.external.NotificationService;
import ch.juventus.mocks.external.PaymentService;
import ch.juventus.mocks.model.Booking;
import ch.juventus.mocks.model.BookingStatus;
import ch.juventus.mocks.model.Screening;
import ch.juventus.mocks.repository.BookingRepository;
import ch.juventus.mocks.repository.ScreeningRepository;
import ch.juventus.mocks.service.BookingService;
import ch.juventus.mocks.service.PriceCalculator;

@ExtendWith(MockitoExtension.class)
class Exercise03SolutionTest {

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
	void bookTickets_throwsExceptionAndSendsNoMail_whenPaymentFails() {
		when(screeningRepository.findById(1L)).thenReturn(Optional.of(DUNE));
		when(priceCalculator.calculateTotal(DUNE, 2)).thenReturn(36.0);
		when(paymentService.charge("anna@example.com", 36.0)).thenThrow(new PaymentFailedException("Card declined"));

		PaymentFailedException exception = assertThrows(PaymentFailedException.class,
				() -> bookingService.bookTickets(1L, "anna@example.com", 2));

		assertEquals("Card declined", exception.getMessage());
		verifyNoInteractions(notificationService);
	}

	@Test
	void bookTickets_secondAttemptSucceeds_afterFirstPaymentFailed() {
		when(screeningRepository.findById(1L)).thenReturn(Optional.of(DUNE));
		when(priceCalculator.calculateTotal(DUNE, 2)).thenReturn(36.0);
		when(paymentService.charge("anna@example.com", 36.0))
				.thenThrow(new PaymentFailedException("Timeout"))
				.thenReturn("TX-2");

		assertThrows(PaymentFailedException.class, () -> bookingService.bookTickets(1L, "anna@example.com", 2));
		Booking booking = bookingService.bookTickets(1L, "anna@example.com", 2);

		assertEquals("TX-2", booking.getPaymentTransactionId());
	}

	@Test
	void cancelBooking_keepsBookingConfirmed_whenRefundFails() {
		Booking booking = new Booking("b1", 1, "anna@example.com", 2, 36.0, "TX-1");
		when(bookingRepository.findById("b1")).thenReturn(Optional.of(booking));
		doThrow(new PaymentFailedException("Refund rejected")).when(paymentService).refund("TX-1", 36.0);

		assertThrows(PaymentFailedException.class, () -> bookingService.cancelBooking("b1"));

		assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
		verify(bookingRepository, never()).save(booking);
		verifyNoInteractions(notificationService);
	}

}
