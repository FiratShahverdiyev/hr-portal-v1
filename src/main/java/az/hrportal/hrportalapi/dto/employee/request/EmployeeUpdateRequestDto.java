package az.hrportal.hrportalapi.dto.employee.request;

import az.hrportal.hrportalapi.domain.employee.Country;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeUpdateRequestDto {
    ForeignPassportRequestDto foreignPassport;
    IDCardRequestDto idCard;
    AddressRequestDto address;
    ContactInfoRequestDto contactInfo;
    BusinessRequestDto business;
    EducationRequestDto education;
    List<FamilyMemberRequestDto> familyMembers;
    List<CertificateRequestDto> certificates;
    List<GovernmentAchievementRequestDto> governmentAchievements;

    Integer familyCondition;
    Integer militaryAchievement;
    Integer gender;
    Integer bloodGroup;
    Integer driverCardCategory;

    String photo;
    String fullName;
    String birthDay;
    String birthPlace;
    String permission;
    List<String> quotas;
    String kvota;

//    Position position; TODO bax
    String driverCardEndDate;
    Country citizenCountry;

    boolean isPrisoner;
    boolean isMemberOfColleaguesAlliance;
}
