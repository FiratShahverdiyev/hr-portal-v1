package az.hrportal.hrportalapi.repository.employee;

import az.hrportal.hrportalapi.domain.employee.Employee;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Override
    @EntityGraph(attributePaths = {"governmentAchievements", "certificates",
            "familyMembers", "quotas", "citizenCountry"})
    @Cacheable("employees")
    List<Employee> findAll();
}
