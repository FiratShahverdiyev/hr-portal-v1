package az.hrportal.hrportalapi.service.position;

import az.hrportal.hrportalapi.constant.position.Level;
import az.hrportal.hrportalapi.domain.position.Skill;
import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.repository.position.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class SkillService {
    private final SkillRepository skillRepository;

    public Integer save(String skillName) {
        log.info("save (Skill) service started with skill : {}", skillName);
        Skill skill = new Skill();
        skill.setName(skillName);
        skill.setLevel(Level.GOOD);
        Skill saved = skillRepository.save(skill);
        log.info("********** save (Skill) service completed with skill : {}, id : {} **********",
                skillName, saved.getId());
        return saved.getId();
    }

    public Set<KeyValue<String, String>> getAll() {
        log.info("getAll service started");
        HashSet<KeyValue<String, String>> response = new HashSet<>();
        for (Skill skill : skillRepository.findAll()) {
            response.add(new KeyValue<>(skill.getName(), skill.getLevel().toString()));
        }
        log.info("********** getAll service completed **********");
        return response;
    }

    public Integer delete(Integer id) {
        log.info("delete (Skill) service started with id : {}", id);
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Skill.class, id));
        skillRepository.delete(skill);
        log.info("********** save (Skill) service completed with id : {}, ", id);
        return id;
    }
}
