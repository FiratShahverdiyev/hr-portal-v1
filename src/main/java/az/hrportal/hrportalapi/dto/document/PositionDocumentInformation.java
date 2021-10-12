package az.hrportal.hrportalapi.dto.document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PositionDocumentInformation {
    String departmentName;
    String subDepartmentName;
    String vacancyName;
    Integer vacancyCount;
    float salary;
    float additionalSalary;
    String workMode;
    String vacancyCategory;
    String workPlace;
}
