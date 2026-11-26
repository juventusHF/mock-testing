package ch.juventus.mocks.solutions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

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
class Exercise06SolutionTest {

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

	@Spy
	private PriceCalculator priceCalculator;

	@InjectMocks
	private BookingService bookingService;

	@Captor
	private ArgumentCaptor<Booking> bookingCaptor;

	@Captor
	private ArgumentCaptor<String> emailCaptor;

	@Captor
	private ArgumentCaptor<Double> amountCaptor;

	@Test
	void bookTickets_savesBookingWithCorrectData() {
		when(screeningRepository.findById(2L)).thenReturn(Optional.of(DUNE_EVENING));
		when(paymentService.charge("anna@example.com", 42.0)).thenReturn("TX-1");

		bookingService.bookTickets(2L, "anna@example.com", 2);

		verify(bookingRepository).save(bookingCaptor.capture());
		Booking saved = bookingCaptor.getValue();
		assertNotNull(saved.getId());
		assertEquals(2L, saved.getScreeningId());
		assertEquals("anna@example.com", saved.getCustomerEmail());
		assertEquals(2, saved.getSeats());
		assertEquals(42.0, saved.getTotalPrice());
		assertEquals("TX-1", saved.getPaymentTransactionId());
		assertEquals(BookingStatus.CONFIRMED, saved.getStatus());
	}

	@Test
	void bookTickets_chargesGroupPriceForEveningScreening() {
		when(screeningRepository.findById(2L)).thenReturn(Optional.of(DUNE_EVENING));

		bookingService.bookTickets(2L, "group@example.com", 5);

		verify(paymentService).charge(emailCaptor.capture(), amountCaptor.capture());
		assertEquals("group@example.com", emailCaptor.getValue());
		// (18 + 3) * 5 = 105, minus 10% group discount
		assertEquals(94.5, amountCaptor.getValue());
	}

	@Test
	void bookTickets_createsUniqueBookingIds() {
		when(screeningRepository.findById(2L)).thenReturn(Optional.of(DUNE_EVENING));

		bookingService.bookTickets(2L, "anna@example.com", 1);
		bookingService.bookTickets(2L, "ben@example.com", 3);

		verify(bookingRepository, times(2)).save(bookingCaptor.capture());
		List<Booking> saved = bookingCaptor.getAllValues();
		assertEquals(2, saved.size());
		assertEquals("anna@example.com", saved.get(0).getCustomerEmail());
		assertEquals("ben@example.com", saved.get(1).getCustomerEmail());
		assertNotEquals(saved.get(0).getId(), saved.get(1).getId());
	}

}
