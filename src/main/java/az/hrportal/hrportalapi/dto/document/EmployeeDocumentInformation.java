package az.hrportal.hrportalapi.dto.document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeDocumentInformation {
    String departmentName;
    String subDepartmentName;
    String vacancyName;
    float salary;
    float additionalSalary;
    float ownAdditionalSalary;
    String fullName;
    String workMode;
}
