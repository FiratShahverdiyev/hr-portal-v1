package az.hrportal.hrportalapi.repository;

import az.hrportal.hrportalapi.domain.operation.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Integer> {
}
