package az.hrportal.hrportalapi.mapper.position;

import az.hrportal.hrportalapi.domain.position.Skill;
import az.hrportal.hrportalapi.dto.position.request.SkillRequestDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
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
    Skill toSkill(SkillRequestDto skillRequestDto);

}
