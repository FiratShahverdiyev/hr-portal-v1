package az.hrportal.hrportalapi.repository.position;

import az.hrportal.hrportalapi.domain.position.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {
    Optional<Speciality> findByName(String name);
}
