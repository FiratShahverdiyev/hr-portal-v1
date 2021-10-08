package az.hrportal.hrportalapi.dto.employee.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeSalaryResponseDto {
    Integer id;
    String fullName;
    String vacancyName;
    Float netSalary;
    Float grossSalary;
}
