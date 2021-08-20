package az.hrportal.hrportalapi.dto.employee;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusinessRequestDto {
    String company;
    String section;
    String subSection;
    String position;
    String jobStartDate;
    String jobEndDate;
    String jobEndReason;
    boolean isMainJob;
}
