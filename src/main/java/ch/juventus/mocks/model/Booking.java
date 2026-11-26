package ch.juventus.mocks.model;

/**
 * A customer's reservation of one or more seats for a screening.
 */
public class Booking {

	private final String id;
	private final long screeningId;
	private final String customerEmail;
	private final int seats;
	private final double totalPrice;
	private final String paymentTransactionId;
	private BookingStatus status;

	public Booking(String id, long screeningId, String customerEmail, int seats, double totalPrice,
			String paymentTransactionId) {
		this.id = id;
		this.screeningId = screeningId;
		this.customerEmail = customerEmail;
		this.seats = seats;
		this.totalPrice = totalPrice;
		this.paymentTransactionId = paymentTransactionId;
		this.status = BookingStatus.CONFIRMED;
	}

	public void cancel() {
		this.status = BookingStatus.CANCELLED;
	}

	public boolean isConfirmed() {
		return status == BookingStatus.CONFIRMED;
	}

	public String getId() {
		return id;
	}

	public long getScreeningId() {
		return screeningId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public int getSeats() {
		return seats;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public String getPaymentTransactionId() {
		return paymentTransactionId;
	}

	public BookingStatus getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "Booking{id='%s', screeningId=%d, customerEmail='%s', seats=%d, totalPrice=%.2f, status=%s}"
				.formatted(id, screeningId, customerEmail, seats, totalPrice, status);
	}

}
