package az.hrportal.hrportalapi.dto.position.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class LanguageKnowledgeResponseDto {
    String name;
    String level;
}
