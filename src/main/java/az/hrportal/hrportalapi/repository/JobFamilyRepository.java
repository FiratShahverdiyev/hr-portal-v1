package az.hrportal.hrportalapi.repository;

import az.hrportal.hrportalapi.domain.position.JobFamily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobFamilyRepository extends JpaRepository<JobFamily, Integer> {
}
