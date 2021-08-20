package az.hrportal.hrportalapi.repository.employee;

import az.hrportal.hrportalapi.domain.employee.ForeignPassport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForeignPassportRepository extends JpaRepository<ForeignPassport, Integer> {
}
