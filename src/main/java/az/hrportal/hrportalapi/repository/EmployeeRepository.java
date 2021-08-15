package az.hrportal.hrportalapi.repository;

import az.hrportal.hrportalapi.domain.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
