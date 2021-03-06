package az.hrportal.hrportalapi.dto.document;

import az.hrportal.hrportalapi.constant.DisciplineType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
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

    Integer newTerm; //deyisti
    Float financialHelp;
    Float achievementAmount; //deyisti

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
    String assignmentTerm; //deyisti
    Float alternateWorkerSalary;
    Float amount;
    String businessTripLocation;
    String eventToBusinessTripDate;
    String eventFromBusinessTripDate;
    Integer businessTripTerm;
    //i????inin yolda ke??irdiyi istirah??t g??n??n?? t??sad??f etdiyi tarix
    String nonWorkDay;
    //ezamiyy?? m??dd??tind?? yolda ke??irilmi?? istirah??t g??n??n??n ??v??zin?? verilmi?? istirah??t g??n??
    String givenNonWorkDay;
    String otherNotes;

    Integer serialNumber1;
    Integer serialNumber2;

    String eventFrom2;
    String eventTo2;
    Integer dayInEvent2;
    String reason;
    String vacationReason;
}
