package az.hrportal.hrportalapi.repository.position;

import az.hrportal.hrportalapi.domain.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Employee, Integer> {
}
