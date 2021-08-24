package az.hrportal.hrportalapi.mapper.position.helper;

import az.hrportal.hrportalapi.constant.position.WorkPlace;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

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
