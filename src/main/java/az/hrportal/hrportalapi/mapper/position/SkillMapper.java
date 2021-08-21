package az.hrportal.hrportalapi.mapper.position;

import az.hrportal.hrportalapi.constant.position.Level;
import az.hrportal.hrportalapi.domain.position.Skill;
import az.hrportal.hrportalapi.dto.position.request.SkillRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface SkillMapper {
    @Mapping(target = "level", source = "level", qualifiedByName = "intToLevel")
    List<Skill> toSkills(List<SkillRequestDto> skillRequestDtos);

    @Named("intToLevel")
    default Level intToLevel(Integer level) {
        return Level.intToEnum(level);
    }
}
