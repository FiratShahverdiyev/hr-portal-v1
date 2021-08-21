package az.hrportal.hrportalapi.repository.position;

import az.hrportal.hrportalapi.domain.position.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
    Optional<Vacancy> findByName(String name);
}
