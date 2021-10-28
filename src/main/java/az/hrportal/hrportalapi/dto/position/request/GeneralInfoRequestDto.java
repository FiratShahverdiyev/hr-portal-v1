package az.hrportal.hrportalapi.dto.position.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    String subDepartmentName;
    String obeyDepartmentName; // TODO Enum ??
    @NotEmpty
    String vacancyName;
    @NotNull
    Integer vacancyCount;
    @Range(min = 1, max = 3)
    Integer workCalculateDegree;
    String subWorkCalculateDegree;
    @NotNull
    float salary;
    @NotEmpty
    String workCondition;
    float additionalSalary;
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
    String requireFile;
    float height;
    boolean healthy;
    boolean militaryAchieve;
    String genderDemand;
    @NotEmpty
    List<String> functionalities;
}
