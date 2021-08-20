package az.hrportal.hrportalapi.repository.employee;

import az.hrportal.hrportalapi.domain.employee.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactInfoRepository extends JpaRepository<ContactInfo, Integer> {
}
