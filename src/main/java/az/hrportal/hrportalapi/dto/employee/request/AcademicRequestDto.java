package az.hrportal.hrportalapi.dto.employee.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AcademicRequestDto {
    boolean higherEducation;
    String academicDegreeDate;
    String academicDegreeNumber;
    String academicDegreeOrganization;
    String institution;
    String faculty;
    String direction;
    String speciality;
    String entranceDate;
    String graduateDate;
    String degree;
    String graduateFileNumber;
    String graduateFileDate;
    String educationType;
    String nostrifikasiyaNumber;
    List<CertificateRequestDto> certificates;
    List<GovernmentAchievementRequestDto> governmentAchievements;
    String driverCardCategory;
    String driverCardEndDate;
    List<@Range(min = 1, max = 9) Integer> quotas;
    boolean isPrisoner;
    boolean isMemberOfColleaguesAlliance;
}
