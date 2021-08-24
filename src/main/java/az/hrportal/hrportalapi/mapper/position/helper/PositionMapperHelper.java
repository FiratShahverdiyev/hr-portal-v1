package az.hrportal.hrportalapi.mapper.position.helper;

import az.hrportal.hrportalapi.constant.position.WorkPlace;
import az.hrportal.hrportalapi.domain.embeddable.ComputerKnowledge;
import az.hrportal.hrportalapi.domain.embeddable.LanguageKnowledge;
import az.hrportal.hrportalapi.domain.embeddable.LegislationStatement;
import az.hrportal.hrportalapi.domain.position.Skill;
import az.hrportal.hrportalapi.dto.position.request.ComputerKnowledgeRequestDto;
import az.hrportal.hrportalapi.dto.position.request.LanguageKnowledgeRequestDto;
import az.hrportal.hrportalapi.dto.position.request.LegislationStatementRequestDto;
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
public interface PositionMapperHelper {
    @IntToWorkPlace
    default WorkPlace intToWorkPlace(Integer workPlace) {
        return WorkPlace.intToEnum(workPlace);
    }
/*

    @Named("toComputerKnowledge")
    ComputerKnowledge toComputerKnowledge(ComputerKnowledgeRequestDto computerKnowledgeRequestDto);

    @IterableMapping(qualifiedByName = "toComputerKnowledge")
    List<ComputerKnowledge> toComputerKnowledgeList(List<ComputerKnowledgeRequestDto> computerKnowledgeRequestDtos);

    @Named("toLanguageKnowledge")
    LanguageKnowledge toLanguageKnowledge(LanguageKnowledgeRequestDto languageKnowledgeRequestDto);

    @IterableMapping(qualifiedByName = "toLanguageKnowledge")
    List<LanguageKnowledge> toLanguageKnowledgeList(List<LanguageKnowledgeRequestDto> languageKnowledgeRequestDtos);

    @Named("toLegislationStatement")
    LegislationStatement toLegislationStatement(LegislationStatementRequestDto legislationStatementRequestDto);

    @IterableMapping(qualifiedByName = "toLegislationStatement")
    List<LegislationStatement> toLegislationStatements(List<LegislationStatementRequestDto>
                                                               legislationStatementRequestDtos);

    @IterableMapping(qualifiedByName = "toSkillResponseDto")
    List<SkillResponseDto> toSkillResponseDtos(List<Skill> skills);

    @Named("toSkillResponseDto")
    @Mapping(target = "skillId", source = "id")
    SkillResponseDto toSkillResponseDto(Skill skill);
*/

}
