package az.hrportal.hrportalapi.dto.position.request;

import lombok.*;
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
