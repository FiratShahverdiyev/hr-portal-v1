package az.hrportal.hrportalapi.repository;

import az.hrportal.hrportalapi.domain.employee.GrossSalary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrossSalaryRepository extends JpaRepository<GrossSalary, Integer> {
}
