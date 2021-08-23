package az.hrportal.hrportalapi.dto.employee.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IDCardResponseDto {
    String series;
    String number;
    String startDate;
    String endDate;
    String pin;
    String organization;
}
