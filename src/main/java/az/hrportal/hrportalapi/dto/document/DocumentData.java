package az.hrportal.hrportalapi.dto.document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class DocumentData {
    @NotNull
    @Range(min = 1, max = 14)
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
    //ownAdditionalSalary

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
    String workMode;
}