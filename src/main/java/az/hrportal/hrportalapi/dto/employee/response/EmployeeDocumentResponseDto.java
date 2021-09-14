package az.hrportal.hrportalapi.dto.employee.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeDocumentResponseDto {
    String department;
    String subDepartment;
    String position; //Qebul oldugu vakansiya
    Integer compensation;
}
