package az.hrportal.hrportalapi.mapper.position;

import az.hrportal.hrportalapi.domain.embeddable.ComputerKnowledge;
import az.hrportal.hrportalapi.domain.embeddable.LanguageKnowledge;
import az.hrportal.hrportalapi.domain.embeddable.LegislationStatement;
import az.hrportal.hrportalapi.dto.position.request.ComputerKnowledgeRequestDto;
import az.hrportal.hrportalapi.dto.position.request.LanguageKnowledgeRequestDto;
import az.hrportal.hrportalapi.dto.position.request.LegislationStatementRequestDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface KnowledgeMapper {
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
}
