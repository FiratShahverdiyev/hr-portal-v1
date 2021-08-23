package az.hrportal.hrportalapi.dto.employee.response;

import az.hrportal.hrportalapi.dto.employee.request.CertificateRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.GovernmentAchievementRequestDto;
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
public class AcademicInfoResponseDto {
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
    List<CertificateResponseDto> certificates;
    List<GovernmentAchievementResponseDto> governmentAchievements;
    String driverCardCategory;
    String driverCardEndDate;
    List<String> quotas;
    boolean isPrisoner;
    boolean isMemberOfColleaguesAlliance;
}
