package az.hrportal.hrportalapi.repository;

import az.hrportal.hrportalapi.domain.employee.Address;
import az.hrportal.hrportalapi.domain.employee.ForeignPassport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForeignPassportRepository extends JpaRepository<ForeignPassport, Integer> {
}
