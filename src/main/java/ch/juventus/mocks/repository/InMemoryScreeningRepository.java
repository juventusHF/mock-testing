package ch.juventus.mocks.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import ch.juventus.mocks.model.Screening;

@Repository
public class InMemoryScreeningRepository implements ScreeningRepository {

	private final Map<Long, Screening> screenings = new ConcurrentHashMap<>();

	public InMemoryScreeningRepository() {
		save(new Screening(1, "Dune: Part Two", LocalDateTime.of(2026, 12, 5, 17, 30), 80, 18.0));
		save(new Screening(2, "Dune: Part Two", LocalDateTime.of(2026, 12, 5, 20, 45), 80, 18.0));
		save(new Screening(3, "Inside Out 2", LocalDateTime.of(2026, 12, 6, 14, 0), 120, 15.0));
		save(new Screening(4, "Oppenheimer", LocalDateTime.of(2026, 12, 6, 21, 0), 50, 20.0));
	}

	@Override
	public Optional<Screening> findById(long id) {
		return Optional.ofNullable(screenings.get(id));
	}

	@Override
	public List<Screening> findAll() {
		return new ArrayList<>(screenings.values());
	}

	@Override
	public void save(Screening screening) {
		screenings.put(screening.id(), screening);
	}

}
