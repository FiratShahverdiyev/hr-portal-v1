package az.hrportal.hrportalapi.repository;

import az.hrportal.hrportalapi.domain.position.Vacancy;
import az.hrportal.hrportalapi.dto.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
}
