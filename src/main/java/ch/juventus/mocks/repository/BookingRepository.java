package ch.juventus.mocks.repository;

import java.util.List;
import java.util.Optional;

import ch.juventus.mocks.model.Booking;

public interface BookingRepository {

	Optional<Booking> findById(String id);

	List<Booking> findByScreeningId(long screeningId);

	void save(Booking booking);

}
