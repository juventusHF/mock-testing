package ch.juventus.mocks.solutions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
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
class Exercise05SolutionTest {

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
	void bookTickets_usesTransactionIdFromPaymentService() {
		when(screeningRepository.findById(anyLong())).thenReturn(Optional.of(DUNE));
		when(priceCalculator.calculateTotal(any(Screening.class), anyInt())).thenReturn(50.0);
		when(paymentService.charge(anyString(), anyDouble())).thenReturn("TX-42");

		Booking booking = bookingService.bookTickets(1L, "anna@example.com", 3);

		assertEquals("TX-42", booking.getPaymentTransactionId());
		verify(notificationService).sendBookingConfirmation(any(Booking.class));
	}

	@Test
	void bookTickets_chargesTheCustomer_matchersMustBeUsedForAllArguments() {
		when(screeningRepository.findById(anyLong())).thenReturn(Optional.of(DUNE));

		bookingService.bookTickets(1L, "anna@example.com", 2);

		// verify(paymentService).charge("anna@example.com", anyDouble()); -> InvalidUseOfMatchersException
		verify(paymentService).charge(eq("anna@example.com"), anyDouble());
		verify(paymentService).charge(endsWith("@example.com"), anyDouble());
	}

	@Test
	void bookTickets_savesConfirmedBookingWithRequestedSeats() {
		when(screeningRepository.findById(anyLong())).thenReturn(Optional.of(DUNE));

		bookingService.bookTickets(1L, "anna@example.com", 3);

		verify(bookingRepository).save(argThat(booking -> booking.getSeats() == 3 && booking.isConfirmed()));
	}

}
