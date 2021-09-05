package az.hrportal.hrportalapi.repository.employee;

import az.hrportal.hrportalapi.domain.employee.AddressCountry;
import az.hrportal.hrportalapi.domain.employee.CitizenCountry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressCountryRepository extends JpaRepository<AddressCountry, Integer> {
}
