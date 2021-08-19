package az.hrportal.hrportalapi.repository;

import az.hrportal.hrportalapi.domain.employee.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
