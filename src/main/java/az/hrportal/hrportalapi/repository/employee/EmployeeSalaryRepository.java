package az.hrportal.hrportalapi.repository.employee;

import az.hrportal.hrportalapi.domain.employee.EmployeeSalary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary, Integer> {
    List<EmployeeSalary> findAllByBackupIsFalse();
}
