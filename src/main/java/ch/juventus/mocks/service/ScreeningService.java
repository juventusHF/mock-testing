package ch.juventus.mocks.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import ch.juventus.mocks.exception.ScreeningNotFoundException;
import ch.juventus.mocks.model.Screening;
import ch.juventus.mocks.repository.ScreeningRepository;

@Service
public class ScreeningService {

	private final ScreeningRepository screeningRepository;

	public ScreeningService(ScreeningRepository screeningRepository) {
		this.screeningRepository = screeningRepository;
	}

	public Screening getScreening(long id) {
		return screeningRepository.findById(id)
				.orElseThrow(() -> new ScreeningNotFoundException(id));
	}

	/**
	 * Finds all screenings whose title contains the search text (case-insensitive), ordered by start time.
	 */
	public List<Screening> findByTitle(String searchText) {
		String lowerCaseSearch = searchText.toLowerCase();
		return screeningRepository.findAll().stream()
				.filter(screening -> screening.movieTitle().toLowerCase().contains(lowerCaseSearch))
				.sorted(Comparator.comparing(Screening::startTime))
				.toList();
	}

}
