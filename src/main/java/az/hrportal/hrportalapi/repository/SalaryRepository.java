package az.hrportal.hrportalapi.repository;

import az.hrportal.hrportalapi.domain.position.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {
}
