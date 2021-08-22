package az.hrportal.hrportalapi.dto.employee.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class FamilyMemberRequestDto {
    String relationType;
    String fullName;
    String birthday;
    String birthplace;
    String workPlace;
    String position;
    String address;
}
