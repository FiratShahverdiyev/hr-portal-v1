package az.hrportal.hrportalapi.dto.employee.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AcademicRequestDto {
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
    Integer educationType;
    String nostrifikasiyaNumber;
    List<CertificateRequestDto> certificates;
    List<GovernmentAchievementRequestDto> governmentAchievements;
    Integer driverCardCategory;
    String driverCardEndDate;
    List<Integer> quotas;
    boolean isPrisoner;
    boolean isMemberOfColleaguesAlliance;
}
