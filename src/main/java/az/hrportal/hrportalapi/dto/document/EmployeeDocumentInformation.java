package az.hrportal.hrportalapi.dto.document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeDocumentInformation {
    String departmentName;
    String subDepartmentName;
    String vacancyName;
    BigDecimal salary;
    BigDecimal additionalSalary;
    BigDecimal ownAdditionalSalary;
}
