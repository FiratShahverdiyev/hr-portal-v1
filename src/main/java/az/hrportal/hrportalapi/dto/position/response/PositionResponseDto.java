package az.hrportal.hrportalapi.dto.position.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PositionResponseDto {
    Integer id;
    String departmentName;
    String subDepartmentName;
    String vacancyName;
    Integer vacancyCount;
    float salary;
    String status;
}
