package az.hrportal.hrportalapi.repository.position;

import az.hrportal.hrportalapi.domain.position.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Optional<Department> findByName(String name);
}
