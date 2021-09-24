package az.hrportal.hrportalapi.mapper.position.helper;

import az.hrportal.hrportalapi.constant.position.*;
import az.hrportal.hrportalapi.domain.embeddable.ComputerKnowledge;
import az.hrportal.hrportalapi.domain.embeddable.LanguageKnowledge;
import az.hrportal.hrportalapi.domain.embeddable.LegislationStatement;
import az.hrportal.hrportalapi.domain.position.Skill;
import az.hrportal.hrportalapi.dto.position.response.ComputerKnowledgeResponseDto;
import az.hrportal.hrportalapi.dto.position.response.LanguageKnowledgeResponseDto;
import az.hrportal.hrportalapi.dto.position.response.LegislationStatementResponseDto;
import az.hrportal.hrportalapi.dto.position.response.SkillResponseDto;
import org.mapstruct.*;

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

    @RequireFileValue
    default String getRequiredFileValue(RequireFile requireFile) {
        return requireFile.getValue();
    }

    @SkillsToDto
    @IterableMapping(qualifiedByName = "toSkillResponseDto")
    List<SkillResponseDto> toSkillResponseDtos(Set<Skill> skills);

    @Named("toSkillResponseDto")
    @Mapping(target = "level", source = "level", qualifiedBy = LevelValue.class)
    @Mapping(target = "skill", source = "name")
    SkillResponseDto toSkillResponseDto(Skill skill);

    @ComputerKnowledgeToDto
    @IterableMapping(qualifiedByName = "toComputerKnowledgeResponseDto")
    Set<ComputerKnowledgeResponseDto> toComputerKnowledgeResponseDtos(Set<ComputerKnowledge> computerKnowledgeSet);

    @Named("toComputerKnowledgeResponseDto")
    @Mapping(target = "level", source = "level", qualifiedBy = LevelValue.class)
    ComputerKnowledgeResponseDto toComputerKnowledgeResponseDto(ComputerKnowledge computerKnowledge);

    @LanguageKnowledgeToDto
    @IterableMapping(qualifiedByName = "toLanguageKnowledgeResponseDto")
    Set<LanguageKnowledgeResponseDto> toLanguageKnowledgeResponseDtos(Set<LanguageKnowledge> languageKnowledgeSet);

    @Named("toLanguageKnowledgeResponseDto")
    @Mapping(target = "level", source = "level", qualifiedBy = LevelValue.class)
    LanguageKnowledgeResponseDto toLanguageKnowledgeResponseDto(LanguageKnowledge languageKnowledge);

    @LegislationStatementToDto
    @IterableMapping(qualifiedByName = "toLegislationStatementResponseDto")
    Set<LegislationStatementResponseDto> toLegislationStatementResponseDtos(Set<LegislationStatement>
                                                                                     legislationStatementSet);

    @Named("toLegislationStatementResponseDto")
    @Mapping(target = "level", source = "level", qualifiedBy = LevelValue.class)
    LegislationStatementResponseDto toLegislationStatementResponseDto(LegislationStatement legislationStatement);
}
