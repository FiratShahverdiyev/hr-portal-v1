package az.hrportal.hrportalapi.mapper.position.helper;

import az.hrportal.hrportalapi.constant.position.EducationDegree;
import az.hrportal.hrportalapi.constant.position.GenderDemand;
import az.hrportal.hrportalapi.constant.position.Level;
import az.hrportal.hrportalapi.constant.position.VacancyCategory;
import az.hrportal.hrportalapi.constant.position.WorkCondition;
import az.hrportal.hrportalapi.constant.position.WorkMode;
import az.hrportal.hrportalapi.constant.position.WorkPlace;
import az.hrportal.hrportalapi.domain.position.Skill;
import az.hrportal.hrportalapi.dto.position.response.SkillResponseDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface PositionMapperHelper {
    @IntToWorkPlace
    default WorkPlace intToWorkPlace(Integer workPlace) {
        return WorkPlace.intToEnum(workPlace);
    }

    @GenderDemandValue
    default String getGenderDemandValue(GenderDemand genderDemand) {
        return genderDemand.getValue();
    }

    @EducationDegreeValue
    default String getEducationDegreeValue(EducationDegree educationDegree) {
        return educationDegree.getValue();
    }

    @LevelValue
    default String getLevelValue(Level level) {
        return level.getValue();
    }

    @VacancyCategoryValue
    default String getVacancyCategoryValue(VacancyCategory vacancyCategory) {
        return vacancyCategory.getValue();
    }

    @WorkConditionValue
    default String getWorkConditionValue(WorkCondition workCondition) {
        return workCondition.getValue();
    }

    @WorkModeValue
    default String getWorkModeValue(WorkMode workMode) {
        return workMode.getValue();
    }

    @SkillsToDto
    @IterableMapping(qualifiedByName = "toSkillResponseDto")
    List<SkillResponseDto> toSkillResponseDtos(Set<Skill> skills);

    @Named("toSkillResponseDto")
    @Mapping(target = "level", source = "level", qualifiedBy = LevelValue.class)
    SkillResponseDto toSkillResponseDto(Skill skill);
}
