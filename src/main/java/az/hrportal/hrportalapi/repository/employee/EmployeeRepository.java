package az.hrportal.hrportalapi.repository.employee;

import az.hrportal.hrportalapi.domain.employee.Employee;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Override
    @EntityGraph(attributePaths = {"governmentAchievements", "certificates",
            "familyMembers", "quotas", "citizenCountry", "position"})
    @Cacheable("employees")
    List<Employee> findAll();

    /*    @Query(value = "select e,ac,ac2,ad from employees e " +
                "inner join address_countries ac on ac.id = e.address_country_id " +
                "inner join address_cities ac2 on ac2.id = e.address_city_id " +
                "inner join address_districts ad on ad.id = e.address_district_id " +
                "where e.active=true offset ?1 limit ?2 ", nativeQuery = true)*/
    @EntityGraph(attributePaths = {"quotas"})
    Page<Employee> findAllByActiveIsTrue(Pageable pageable);

    List<Employee> findAllByActiveIsTrue();
}
