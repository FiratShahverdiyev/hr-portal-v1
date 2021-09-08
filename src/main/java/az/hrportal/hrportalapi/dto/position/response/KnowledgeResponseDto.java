package az.hrportal.hrportalapi.dto.position.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KnowledgeResponseDto {
    Integer id;
    Set<ComputerKnowledgeResponseDto> computerKnowledge;
    Set<LegislationStatementResponseDto> legislationStatements;
    Set<LanguageKnowledgeResponseDto> languageKnowledge;
}
