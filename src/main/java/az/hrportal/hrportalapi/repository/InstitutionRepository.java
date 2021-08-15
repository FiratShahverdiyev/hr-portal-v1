package az.hrportal.hrportalapi.repository;

import az.hrportal.hrportalapi.domain.position.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Integer> {
}
