package az.hrportal.hrportalapi.dto.document;

import az.hrportal.hrportalapi.constant.DisciplineType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class DocumentData {
    @NotNull
    Integer documentType;

    String mainOfOrder;
    String titleDepartment;
    String titleFullName;

    /*CHANGE EMPLOYEE POSITION*/
    Integer employeeId;
    Integer positionId;
    //departmentName
    //vacancyName
    //salary
    //additionalSalary
    //ownAdditionalSalary
    String changeDate;
    //newDepartmentName
    //newVacancyName
    //newSalary
    //newAdditionalSalary
    BigDecimal newOwnAdditionalSalary;

    /*CHANGE EMPLOYEE SALARY*/
    //Integer employeeId;
    //departmentName
    //vacancyName
    //salary
    //additionalSalary
    //ownAdditionalSalary
    //String changeDate;
    BigDecimal newSalary;
    BigDecimal newAdditionalSalary;
    //BigDecimal newOwnAdditionalSalary;

    /*CREATE POSITION*/
    //Integer positionId;
    //departmentName
    //subDepartmentName
    //vacancyName
    //vacancyCount
    //salary
    //WorkMode
    //VacancyCategory
    //WorkPlace

    /*DELETE POSITION*/
    //Integer positionId;
    //departmentName
    //subDepartmentName
    //vacancyName
    //vacancyCount
    //salary
    //WorkMode
    //VacancyCategory
    //WorkPlace

    /*JOIN TO JOB*/
    //Integer employeeId;
    //Integer positionId;
    //fullName
    //departmentName
    //subDepartmentName
    //vacancyName
    String joinDate;
    Integer testPeriod;
    BigDecimal ownAdditionalSalary;
    //salary
    //additionalSalary

    /*LEAVE FROM JOB*/
    //Integer employeeId
    //fullName
    //departmentName
    //subDepartmentName
    //vacancyName
    String dismissalReason;
    String dismissalDate;
    //String compensation
    List<String> notes;

    /*CHANGE WORK MODE*/
    //Integer employeeId
    String newWorkMode;

    /*ADDITIONAL SALARY*/
    //Integer employeeId;
    //departmentName
    //vacancyName
    //salary
    //additionalSalary
    //String changeDate;
    //BigDecimal newSalary;
    //BigDecimal newAdditionalSalary;

    Integer changePeriod;
    BigDecimal financialHelp;
    BigDecimal achievement;

    String eventFrom;
    String eventTo;
    String eventName;
    Integer dayInEvent;
    DisciplineType disciplineType;

    List<Integer> employeeIds;
    String presentationOwnerName;
    String presentationOwnerDepartment;
    String presentationOwnerPosition;

    String callBackReason;
    String callBackDate;

    Integer year;
}
