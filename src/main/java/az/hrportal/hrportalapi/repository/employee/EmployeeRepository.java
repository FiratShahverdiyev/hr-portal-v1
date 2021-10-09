package az.hrportal.hrportalapi.repository.employee;

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

    @Query(value = "select * from employees e where e.active=true offset ?1 limit ?2", nativeQuery = true)
    List<Employee> findAllActiveEmployee(int offset, int limit);

    List<Employee> findAllByActiveIsTrue();

    @Query(value = "select count(e.id) from employees e where e.active=true", nativeQuery = true)
    Integer getActiveEmployeeCount();
}
