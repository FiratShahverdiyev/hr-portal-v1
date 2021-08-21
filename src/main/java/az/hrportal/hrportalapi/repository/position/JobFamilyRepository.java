package az.hrportal.hrportalapi.repository.position;

import az.hrportal.hrportalapi.domain.position.JobFamily;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobFamilyRepository extends JpaRepository<JobFamily, Integer> {
    Optional<JobFamily> findByName(String name);
}
