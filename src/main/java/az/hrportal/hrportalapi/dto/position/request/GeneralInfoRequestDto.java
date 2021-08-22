package az.hrportal.hrportalapi.dto.position.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class GeneralInfoRequestDto {
    @NotEmpty
    String institutionName;
    @NotEmpty
    String departmentName;
    @NotEmpty
    String subDepartmentName;
    //    @NotEmpty
    String obeyDepartmentName; // TODO Enum ??
    @NotEmpty
    String vacancyName;
    @NotNull
    Integer vacancyCount;
    @Range(min = 1, max = 3)
    Integer workCalculateDegree;
    Integer subWorkCalculateDegree;
    @NotNull
    BigDecimal salary;
    @NotNull
    Integer workCondition;
    BigDecimal additionalSalary;
    @NotNull
    Integer workMode;
    @NotNull
    Integer vacancyCategory;
    @NotEmpty
    String jobFamily;
    List<SkillRequestDto> skills;
    @NotNull
    Integer workPlace;
    @NotEmpty
    String fullNameAndPosition;
    Integer areaExperience;
    Integer leaderExperience;
    Integer educationDegree;
    String educationSpeciality;
    Float height;
    boolean healthy;
    boolean militaryAchieve;
    Integer genderDemand;
    @NotEmpty
    List<String> functionalities;
}
