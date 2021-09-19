package az.hrportal.hrportalapi.dto;

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
    String main;
    //Xitam
    Integer employeeId;
    String dismissalDate;
    String dismissalReason;
    List<String> note;
    //Ishe qebul
    String joinDate;
    Integer testPeriod;
    BigDecimal ownAdditionalSalary;
    //Vezife deyisikliyi
    String changeDate;
    String department;
    String position;
    BigDecimal mainSalary;
    BigDecimal additionalSalary;
    BigDecimal newOwnAdditionalSalary;
    //Emek haqqi deyisikliyi
    //Is yeri deyisikliyi
    String newWorkPlace;
    //Muveqqeti kecirilme
    Integer temporaryPeriod = 1;
    //maddi yardim
    BigDecimal financialHelp;
    //Mukafatlandirm
    BigDecimal achievement;
    //Statin tesisi, legvi
    Integer vacancyCount;
    BigDecimal salary;
    String workMode;
    String vacancyCategory;
    @Range(min = 1, max = 5)
    Integer workPlace;
}
