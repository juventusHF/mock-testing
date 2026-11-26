package ch.juventus.mocks.external;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Stand-in for a real payment provider. Every payment succeeds.
 */
@Service
public class FakePaymentService implements PaymentService {

	private static final Logger log = LoggerFactory.getLogger(FakePaymentService.class);

	@Override
	public String charge(String customerEmail, double amount) {
		String transactionId = "TX-" + UUID.randomUUID();
		log.info("Charged {} CHF from {} ({})", amount, customerEmail, transactionId);
		return transactionId;
	}

	@Override
	public void refund(String transactionId, double amount) {
		log.info("Refunded {} CHF for {}", amount, transactionId);
	}

}
