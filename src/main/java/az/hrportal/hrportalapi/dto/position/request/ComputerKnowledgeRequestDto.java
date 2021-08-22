package az.hrportal.hrportalapi.dto.position.request;

import az.hrportal.hrportalapi.constant.position.Level;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.Embeddable;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class ComputerKnowledgeRequestDto {
    String name;
    Integer level;
}
