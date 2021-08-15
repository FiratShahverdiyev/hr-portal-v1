package az.hrportal.hrportalapi.dto.position;

import az.hrportal.hrportalapi.constant.position.Level;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Embeddable;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class ComputerKnowledge {
    String name;
    Level level;
}
