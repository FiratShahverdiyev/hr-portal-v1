package az.hrportal.hrportalapi.dto.position.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class SkillResponseDto {
    String name;
    String level;
    Integer skillId;
}
