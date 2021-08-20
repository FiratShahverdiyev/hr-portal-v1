package az.hrportal.hrportalapi.repository.position;

import az.hrportal.hrportalapi.domain.position.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {
}
