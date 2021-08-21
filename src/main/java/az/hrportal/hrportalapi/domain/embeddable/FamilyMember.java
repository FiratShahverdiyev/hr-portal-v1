package az.hrportal.hrportalapi.domain.embeddable;

import az.hrportal.hrportalapi.constant.employee.RelationType;
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
public class FamilyMember {
    RelationType relationType;
    String fullName;
    Date birthday;
    String birthPlace;
    String workPlace;
    String position;
    String address;
}
