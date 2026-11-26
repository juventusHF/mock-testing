package ch.juventus.mocks.repository;

import java.util.List;
import java.util.Optional;

import ch.juventus.mocks.model.Screening;

public interface ScreeningRepository {

	Optional<Screening> findById(long id);

	List<Screening> findAll();

	void save(Screening screening);

}
