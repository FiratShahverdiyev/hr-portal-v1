package az.hrportal.hrportalapi.repository.position;

import az.hrportal.hrportalapi.constant.position.Level;
import az.hrportal.hrportalapi.domain.position.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
    boolean existsByName(String name);

    Optional<Skill> findByNameAndLevel(String name, Level level);
}
