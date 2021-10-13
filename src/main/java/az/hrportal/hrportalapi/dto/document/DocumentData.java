package az.hrportal.hrportalapi.dto.document;

import az.hrportal.hrportalapi.constant.DisciplineType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    Float newOwnAdditionalSalary;

    /*CHANGE EMPLOYEE SALARY*/
    //Integer employeeId;
    //departmentName
    //vacancyName
    //salary
    //additionalSalary
    //ownAdditionalSalary
    //String changeDate;
    Float newSalary;
    Float newAdditionalSalary;
    //Float newOwnAdditionalSalary;

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
    Float ownAdditionalSalary;
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
    //Float newSalary;
    //Float newAdditionalSalary;

    Integer changePeriod;
    Float financialHelp;
    Float achievement;

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
    Set<Integer> catchMonths;
    Float catchAmount;
    String assignmentDate;
    Float alternateWorkerSalary;
    Float amount;

    String businessTripLocation;
    String businessTripDate;
    Integer businessTripTerm;
    //işçinin yolda keçirdiyi istirahət gününə təsadüf etdiyi tarix
    String nonWorkDay;
    //ezamiyyə müddətində yolda keçirilmiş istirahət gününün əvəzinə verilmiş istirahət günü
    String givenNonWorkDay;
    String startDateToWork;
    String otherNotes;
}
