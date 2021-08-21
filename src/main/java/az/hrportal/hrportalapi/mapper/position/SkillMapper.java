package az.hrportal.hrportalapi.mapper.position;

import az.hrportal.hrportalapi.constant.position.Level;
import az.hrportal.hrportalapi.domain.position.Skill;
import az.hrportal.hrportalapi.dto.position.request.SkillRequestDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface SkillMapper {
    @IterableMapping(qualifiedByName = "toSkill")
    List<Skill> toSkills(List<SkillRequestDto> skillRequestDtos);

    @Named("toSkill")
    @Mapping(target = "level", source = "level", qualifiedByName = "intToLevel")
    Skill toSkill(SkillRequestDto skillRequestDto);

    @Named("intToLevel")
    default Level intToLevel(Integer level) {
        return Level.intToEnum(level);
    }
}
