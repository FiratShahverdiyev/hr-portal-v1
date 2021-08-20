package az.hrportal.hrportalapi.dto.employee;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForeignPassportRequestDto {
    Integer series;
    String number;
    String startDate;
    String endDate;
}
