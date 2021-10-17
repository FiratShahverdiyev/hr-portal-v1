package az.hrportal.hrportalapi.repository.employee;

import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.employee.EmployeeSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary, Integer> {
    List<EmployeeSalary> findAllByBackupIsFalse();

    @Query("select e from EmployeeSalary e where e.backup=true and month(e.createdAt) < ?1")
    List<EmployeeSalary> findAllByCreateDate(Integer month);


    List<EmployeeSalary> findAllByEmployeeAndBackupIsTrue(Employee employee);
}
