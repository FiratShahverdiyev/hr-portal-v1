package az.hrportal.hrportalapi.repository.employee;

import az.hrportal.hrportalapi.domain.employee.AddressCountry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressCountryRepository extends JpaRepository<AddressCountry, Integer> {
}
