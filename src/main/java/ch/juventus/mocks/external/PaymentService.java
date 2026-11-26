package ch.juventus.mocks.external;

import ch.juventus.mocks.exception.PaymentFailedException;

/**
 * Gateway to an external payment provider.
 */
public interface PaymentService {

	/**
	 * Charges the customer.
	 *
	 * @return the transaction id of the payment
	 * @throws PaymentFailedException if the payment is declined
	 */
	String charge(String customerEmail, double amount);

	/**
	 * Refunds (part of) an earlier payment.
	 *
	 * @throws PaymentFailedException if the refund is rejected
	 */
	void refund(String transactionId, double amount);

}
