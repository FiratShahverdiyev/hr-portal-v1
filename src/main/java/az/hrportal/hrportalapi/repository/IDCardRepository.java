package az.hrportal.hrportalapi.repository;

import az.hrportal.hrportalapi.domain.employee.ForeignPassport;
import az.hrportal.hrportalapi.domain.employee.IDCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDCardRepository extends JpaRepository<IDCard, Integer> {
}
