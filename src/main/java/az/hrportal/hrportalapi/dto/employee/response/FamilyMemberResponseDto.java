package az.hrportal.hrportalapi.dto.employee.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FamilyMemberResponseDto {
    String relationType;
    String fullName;
    String birthday;
    String birthplace;
    String workPlace;
    String position;
    String address;
}
