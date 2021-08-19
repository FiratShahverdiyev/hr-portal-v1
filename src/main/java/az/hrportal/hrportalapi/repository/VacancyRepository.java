package az.hrportal.hrportalapi.repository;

import az.hrportal.hrportalapi.domain.position.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
}
