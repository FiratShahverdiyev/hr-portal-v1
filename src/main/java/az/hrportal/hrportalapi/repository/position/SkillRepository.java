package az.hrportal.hrportalapi.repository.position;

import az.hrportal.hrportalapi.domain.position.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
    Optional<Skill> findByName(String name);
}
