package ch.juventus.mocks.service;

import org.springframework.stereotype.Service;

import ch.juventus.mocks.model.Screening;

/**
 * Pricing rules:
 * <ul>
 * <li>Screenings starting at 20:00 or later cost a surcharge of 3 CHF per seat.</li>
 * <li>Bookings of 5 or more seats get a group discount of 10% on the total.</li>
 * </ul>
 */
@Service
public class PriceCalculator {

	static final int EVENING_START_HOUR = 20;
	static final double EVENING_SURCHARGE = 3.0;
	static final int GROUP_SIZE = 5;
	static final int GROUP_DISCOUNT_PERCENT = 10;

	public double calculateSeatPrice(Screening screening) {
		double price = screening.basePrice();
		if (screening.startTime().getHour() >= EVENING_START_HOUR) {
			price += EVENING_SURCHARGE;
		}
		return price;
	}

	public double calculateTotal(Screening screening, int seats) {
		double total = calculateSeatPrice(screening) * seats;
		if (seats >= GROUP_SIZE) {
			total = total * (100 - GROUP_DISCOUNT_PERCENT) / 100;
		}
		return total;
	}

}
