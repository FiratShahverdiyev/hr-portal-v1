package az.hrportal.hrportalapi.dto.document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GeneralDocumentInformation {
    Integer employeeId;
    Integer positionId;
    String documentType;
    Integer documentId;
    String mainOfOrder;
    String titleDepartment;
    String titleFullName;
    String changeDate;
    float newSalary;
    float newAdditionalSalary;
    float newOwnAdditionalSalary;
    String joinDate;
    Integer testPeriod;
    Integer changePeriod;
    float ownAdditionalSalary;
    String dismissalReason;
    String dismissalDate;
    List<String> notes;
    String newWorkMode;
    String status;
    String eventFrom;
    String eventTo;
    String eventName;
    String disciplineType;
}
