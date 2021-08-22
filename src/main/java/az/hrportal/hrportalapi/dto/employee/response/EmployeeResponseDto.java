package az.hrportal.hrportalapi.dto.employee.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeResponseDto {
    Integer id;
    String photo;
    String familyCondition;
    String militaryAchievement;
    String fullName;
    String birthday;
    String birthplace;
    String citizenCountry;
    String gender;
    String bloodGroup;
    String permission;
    ForeignPassportResponseDto foreignPassport;
    IDCardResponseDto idCard;
    AddressResponseDto address;
    ContactInfoResponseDto contactInfo;
    List<FamilyMemberResponseDto> familyMembers;
    List<String> quotas;
    BusinessResponseDto business;
    EducationResponseDto education;
    String kvota;
    List<CertificateResponseDto> certificates;
    List<GovernmentAchievementResponseDto> governmentAchievements;
    //    Position position;
    String driverCardCategory;
    String driverCardEndDate;
    boolean prisoner;
    boolean memberOfColleaguesAlliance;
}
