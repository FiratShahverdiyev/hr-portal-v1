package az.hrportal.hrportalapi.domain.embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Embeddable;
import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class Certificate {
    String name;
    Date endDate;
}
