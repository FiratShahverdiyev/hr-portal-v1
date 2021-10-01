package az.hrportal.hrportalapi.dto.employee.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeResponseDto {
    Integer id;
    String photo;
    String fullName;
    String department;
    String position;
    Boolean active;
}
