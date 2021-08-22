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
    String subWorkCalculateDegree;
    @NotNull
    BigDecimal salary;
    @NotEmpty
    String workCondition;
    BigDecimal additionalSalary;
    @NotEmpty
    String workMode;
    @NotEmpty
    String vacancyCategory;
    @NotEmpty
    String jobFamily;
    List<SkillRequestDto> skills;
    @NotNull
    @Range(min = 1, max = 5)
    Integer workPlace;
    @NotEmpty
    String fullNameAndPosition;
    Integer areaExperience;
    Integer leaderExperience;
    String educationDegree;
    String educationSpeciality;
    Float height;
    boolean healthy;
    boolean militaryAchieve;
    String genderDemand;
    @NotEmpty
    List<String> functionalities;
}
