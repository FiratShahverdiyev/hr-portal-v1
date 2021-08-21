package az.hrportal.hrportalapi.repository.position;

import az.hrportal.hrportalapi.domain.position.SubDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubDepartmentRepository extends JpaRepository<SubDepartment, Integer> {
    Optional<SubDepartment> findByName(String name);
}
