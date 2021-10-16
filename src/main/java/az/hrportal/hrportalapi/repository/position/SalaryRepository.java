package az.hrportal.hrportalapi.repository.position;

import az.hrportal.hrportalapi.domain.position.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {
    Optional<Salary> findByAmount(float salary);
}
