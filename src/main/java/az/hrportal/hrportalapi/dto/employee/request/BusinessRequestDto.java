package az.hrportal.hrportalapi.dto.employee.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
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
