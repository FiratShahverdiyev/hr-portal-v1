package az.hrportal.hrportalapi.repository;

import az.hrportal.hrportalapi.domain.employee.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Certificate, Integer> {
}
