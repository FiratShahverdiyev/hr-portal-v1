package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.domain.position.Skill;
import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.repository.position.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SkillService {
    private final SkillRepository skillRepository;

    public Integer save(String skillName) {
        log.info("save (Skill) service started with skill : {}", skillName);
        Skill skill = new Skill();
        skill.setName(skillName);
        Skill saved = skillRepository.save(skill);
        log.info("********** save (Skill) service completed with skill : {}, id : {} **********",
                skillName, saved.getId());
        return saved.getId();
    }

    public List<KeyValue<String, Integer>> getAll() {
        log.info("getAllSkills service started");
        List<KeyValue<String, Integer>> response = new ArrayList<>();
        for (Skill skill : skillRepository.findAll()) {
            response.add(new KeyValue<>(skill.getName(), skill.getId()));
        }
        log.info("********** getAllSkills service completed **********");
        return response;
    }
}
