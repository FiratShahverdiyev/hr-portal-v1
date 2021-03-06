package az.hrportal.hrportalapi.dto.employee.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSalaryResponseDto {
    Integer id;
    String fullName;
    String vacancyName;
    float netSalary;
    float grossSalary;
    Integer employeeActiveDayCount;
    Integer activeDayCount;
    float employeeMDSS;
    float positionMDSS;
    float employeeITS;
    float positionITS;
    float employeeUnemploymentTax;
    float positionUnemploymentTax;
    float incomingTax;
    float other;
}
