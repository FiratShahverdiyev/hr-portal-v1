package az.hrportal.hrportalapi.dto.position.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KnowledgeResponseDto {
    List<ComputerKnowledgeResponseDto> computerKnowledge;
    List<LegislationStatementResponseDto> legislationStatements;
    List<LanguageKnowledgeResponseDto> languageKnowledge;
}
