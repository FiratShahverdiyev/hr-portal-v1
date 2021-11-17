package az.hrportal.hrportalapi.repository;

import az.hrportal.hrportalapi.domain.position.EducationInstitution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EducationInstitutionRepository extends JpaRepository<EducationInstitution, Integer> {

    Optional<EducationInstitution> findByName(String name);

}
