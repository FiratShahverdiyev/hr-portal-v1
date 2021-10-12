package az.hrportal.hrportalapi.dto.position.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class GeneralInfoResponseDto {
    Integer id;
    String institutionName;
    String departmentName;
    String subDepartmentName;
    String obeyDepartmentName; // TODO Enum ??
    String vacancyName;
    Integer vacancyCount;
    Integer workCalculateDegree;
    String subWorkCalculateDegree;
    float salary;
    String workCondition;
    float additionalSalary;
    String workMode;
    String vacancyCategory;
    String jobFamily;
    List<SkillResponseDto> skills;
    String workPlace;
    String fullNameAndPosition;
    Integer areaExperience;
    Integer leaderExperience;
    String educationDegree;
    String educationSpeciality;
    float height;
    boolean healthy;
    boolean militaryAchieve;
    String genderDemand;
    String requireFile;
    List<String> functionalities;
}
