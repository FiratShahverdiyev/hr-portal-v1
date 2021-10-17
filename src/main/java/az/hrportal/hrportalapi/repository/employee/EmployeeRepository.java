package az.hrportal.hrportalapi.repository.employee;

import az.hrportal.hrportalapi.constant.EmployeeActivity;
import az.hrportal.hrportalapi.domain.employee.Employee;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Override
    @EntityGraph(attributePaths = {"governmentAchievements", "certificates",
            "familyMembers", "quotas", "citizenCountry", "position"})
    @Cacheable("employees")
    List<Employee> findAll();

    @Query(value = "select count(e.id) from employees e where e.activity='IN'", nativeQuery = true)
    Integer getActiveEmployeeCount();

    List<Employee> findAllByGrossCalculatedIsFalseAndEmployeeActivity(EmployeeActivity employeeActivity);

    List<Employee> findAllByEmployeeActivity(EmployeeActivity employeeActivity);
}
