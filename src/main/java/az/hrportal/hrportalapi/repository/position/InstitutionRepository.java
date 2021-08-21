package az.hrportal.hrportalapi.repository.position;

import az.hrportal.hrportalapi.domain.position.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Integer> {
}
