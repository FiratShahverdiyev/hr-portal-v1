package az.hrportal.hrportalapi.dto.position.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
public class PositionFilterRequestDto {
    String department;
    String subDepartment;
    String vacancy;
    Integer vacancyCount;
}
