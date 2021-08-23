package az.hrportal.hrportalapi.repository.employee;

import az.hrportal.hrportalapi.domain.employee.GovernmentAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GovernmentAchievementRepository extends JpaRepository<GovernmentAchievement, Integer> {
    Optional<GovernmentAchievement> findByNameAndOrganization(String name, String organization);
}
