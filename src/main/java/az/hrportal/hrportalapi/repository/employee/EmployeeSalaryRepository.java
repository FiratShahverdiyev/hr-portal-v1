package az.hrportal.hrportalapi.repository.employee;

import az.hrportal.hrportalapi.domain.employee.EmployeeSalary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary, Integer> {
}
