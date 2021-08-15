package az.hrportal.hrportalapi.repository;

import az.hrportal.hrportalapi.domain.position.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {
}
