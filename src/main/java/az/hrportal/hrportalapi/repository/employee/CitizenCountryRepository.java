package az.hrportal.hrportalapi.repository.employee;

import az.hrportal.hrportalapi.domain.employee.CitizenCountry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CitizenCountryRepository extends JpaRepository<CitizenCountry, Integer> {
    Optional<CitizenCountry> findByName(String name);
}
