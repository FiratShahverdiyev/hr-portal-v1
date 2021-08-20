package az.hrportal.hrportalapi.repository.position;

import az.hrportal.hrportalapi.domain.position.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Integer> {
}
