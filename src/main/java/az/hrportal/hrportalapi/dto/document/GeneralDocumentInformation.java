package az.hrportal.hrportalapi.dto.document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
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
    BigDecimal newSalary;
    BigDecimal newAdditionalSalary;
    BigDecimal newOwnAdditionalSalary;
    String joinDate;
    Integer testPeriod;
    Integer changePeriod;
    BigDecimal ownAdditionalSalary;
    String dismissalReason;
    String dismissalDate;
    List<String> notes;
    String newWorkMode;
}
