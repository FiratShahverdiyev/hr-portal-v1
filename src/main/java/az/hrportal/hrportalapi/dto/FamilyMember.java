package az.hrportal.hrportalapi.dto;

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
    String type; //TODO Enum
    String fullName;
    Date birthDay; // ?
    String birthPlace; //TODO Enum or entity
    String workPlace;
    String position;
    String address;
}
