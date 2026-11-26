package ch.juventus.mocks.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ch.juventus.mocks.exception.BookingNotFoundException;
import ch.juventus.mocks.exception.NotEnoughSeatsException;
import ch.juventus.mocks.exception.ScreeningNotFoundException;
import ch.juventus.mocks.external.NotificationService;
import ch.juventus.mocks.external.PaymentService;
import ch.juventus.mocks.model.Booking;
import ch.juventus.mocks.model.Screening;
import ch.juventus.mocks.repository.BookingRepository;
import ch.juventus.mocks.repository.ScreeningRepository;

@Service
public class BookingService {

	static final int MAX_SEATS_PER_BOOKING = 10;

	private static final Logger log = LoggerFactory.getLogger(BookingService.class);

	private final ScreeningRepository screeningRepository;
	private final BookingRepository bookingRepository;
	private final PaymentService paymentService;
	private final NotificationService notificationService;
	private final PriceCalculator priceCalculator;

	public BookingService(ScreeningRepository screeningRepository, BookingRepository bookingRepository,
			PaymentService paymentService, NotificationService notificationService,
			PriceCalculator priceCalculator) {
		this.screeningRepository = screeningRepository;
		this.bookingRepository = bookingRepository;
		this.paymentService = paymentService;
		this.notificationService = notificationService;
		this.priceCalculator = priceCalculator;
	}

	/**
	 * Returns the number of seats that are not taken by a confirmed booking.
	 */
	public int getAvailableSeats(long screeningId) {
		return availableSeats(findScreening(screeningId));
	}

	/**
	 * Books seats for a screening:
	 * <ol>
	 * <li>validates the number of seats (1 to 10)</li>
	 * <li>checks that enough seats are available</li>
	 * <li>charges the customer</li>
	 * <li>saves the booking</li>
	 * <li>sends a confirmation e-mail (a failing e-mail does not fail the booking)</li>
	 * </ol>
	 */
	public Booking bookTickets(long screeningId, String customerEmail, int seats) {
		if (seats < 1 || seats > MAX_SEATS_PER_BOOKING) {
			throw new IllegalArgumentException(
					"Seats must be between 1 and %d, but was %d".formatted(MAX_SEATS_PER_BOOKING, seats));
		}

		Screening screening = findScreening(screeningId);
		int available = availableSeats(screening);
		if (seats > available) {
			throw new NotEnoughSeatsException(seats, available);
		}

		double totalPrice = priceCalculator.calculateTotal(screening, seats);
		String transactionId = paymentService.charge(customerEmail, totalPrice);

		Booking booking = new Booking(UUID.randomUUID().toString(), screeningId, customerEmail, seats,
				totalPrice, transactionId);
		bookingRepository.save(booking);

		try {
			notificationService.sendBookingConfirmation(booking);
		}
		catch (RuntimeException ex) {
			log.warn("Could not send confirmation for booking {}", booking.getId(), ex);
		}

		return booking;
	}

	/**
	 * Cancels a confirmed booking and refunds the full price.
	 */
	public void cancelBooking(String bookingId) {
		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new BookingNotFoundException(bookingId));
		if (!booking.isConfirmed()) {
			throw new IllegalStateException("Booking %s is already cancelled".formatted(bookingId));
		}

		paymentService.refund(booking.getPaymentTransactionId(), booking.getTotalPrice());
		booking.cancel();
		bookingRepository.save(booking);
		notificationService.sendCancellationConfirmation(booking);
	}

	private Screening findScreening(long screeningId) {
		return screeningRepository.findById(screeningId)
				.orElseThrow(() -> new ScreeningNotFoundException(screeningId));
	}

	private int availableSeats(Screening screening) {
		int bookedSeats = bookingRepository.findByScreeningId(screening.id()).stream()
				.filter(Booking::isConfirmed)
				.mapToInt(Booking::getSeats)
				.sum();
		return screening.totalSeats() - bookedSeats;
	}

}
