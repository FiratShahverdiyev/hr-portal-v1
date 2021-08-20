package az.hrportal.hrportalapi.repository.employee;

import az.hrportal.hrportalapi.domain.employee.IDCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDCardRepository extends JpaRepository<IDCard, Integer> {
}
