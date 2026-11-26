package ch.juventus.mocks.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import ch.juventus.mocks.model.Booking;

@Repository
public class InMemoryBookingRepository implements BookingRepository {

	private final Map<String, Booking> bookings = new ConcurrentHashMap<>();

	@Override
	public Optional<Booking> findById(String id) {
		return Optional.ofNullable(bookings.get(id));
	}

	@Override
	public List<Booking> findByScreeningId(long screeningId) {
		return bookings.values().stream()
				.filter(booking -> booking.getScreeningId() == screeningId)
				.toList();
	}

	@Override
	public void save(Booking booking) {
		bookings.put(booking.getId(), booking);
	}

}
