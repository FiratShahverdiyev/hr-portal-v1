package az.hrportal.hrportalapi.repository;

import az.hrportal.hrportalapi.domain.employee.Address;
import az.hrportal.hrportalapi.domain.employee.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactInfoRepository extends JpaRepository<ContactInfo, Integer> {
}
