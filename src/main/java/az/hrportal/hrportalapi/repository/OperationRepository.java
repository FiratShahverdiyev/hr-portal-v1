package az.hrportal.hrportalapi.repository;

import az.hrportal.hrportalapi.domain.operation.Operation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Integer> {
    @EntityGraph(attributePaths = {"position"})
    @Override
    List<Operation> findAll();
}
