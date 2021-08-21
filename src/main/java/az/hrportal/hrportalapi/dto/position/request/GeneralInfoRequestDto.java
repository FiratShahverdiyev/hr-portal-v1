package az.hrportal.hrportalapi.dto.position.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class GeneralInfoRequestDto {
    String institutionName;
    String departmentName;
    String subDepartmentName;
    String obeyDepartmentName; // TODO Enum ??
    String vacancyName;
    Integer vacancyCount;
    Integer workCalculateDegree;
    Integer subWorkCalculateDegree;
    BigDecimal salary;
    Integer workCondition;
    BigDecimal additionalSalary;
    Integer workMode;
    Integer vacancyCategory;
    String jobFamily;
    List<SkillRequestDto> skills;
    String fullNameAndPosition;
    Integer areaExperience;
    Integer leaderExperience;
    Integer educationDegree;
    String educationSpeciality;
    Float height;
    boolean healthy;
    boolean militaryAchieve;
    Integer genderDemand;
    List<String> functionalities;
}
