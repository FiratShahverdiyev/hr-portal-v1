package az.hrportal.hrportalapi.dto.position.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class KnowledgeRequestDto {
    List<ComputerKnowledgeRequestDto> computerKnowledge;
    List<LegislationStatementRequestDto> legislationStatements;
    List<LanguageKnowledgeRequestDto> languageKnowledge;
}
