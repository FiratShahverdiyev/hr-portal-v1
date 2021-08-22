package az.hrportal.hrportalapi.mapper.position;

import az.hrportal.hrportalapi.domain.position.Position;
import az.hrportal.hrportalapi.domain.position.Skill;
import az.hrportal.hrportalapi.dto.position.response.GeneralInfoResponseDto;
import az.hrportal.hrportalapi.dto.position.response.SkillResponseDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface PositionGeneralInfoMapper {
    @Mapping(target = "institutionName", source = "institution.name")
    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "subDepartmentName", source = "subDepartment.name")
    @Mapping(target = "vacancyName", source = "vacancy.name")
    @Mapping(target = "educationSpeciality", source = "educationSpeciality.name")
    @Mapping(target = "jobFamily", source = "jobFamily.name")
    @Mapping(target = "salary", source = "salary.salary")
    @Mapping(target = "skills", source = "skills", qualifiedByName = "toSkillResponseDtos")
    GeneralInfoResponseDto toGeneralInfoResponseDto(Position position);

    @Named("toSkillResponseDtos")
    @IterableMapping(qualifiedByName = "toSkillResponseDto")
    List<SkillResponseDto> toSkillResponseDtos(List<Skill> skills);

    @Named("toSkillResponseDto")
    @Mapping(target = "skillId", source = "id")
    SkillResponseDto toSkillResponseDto(Skill skill);
}