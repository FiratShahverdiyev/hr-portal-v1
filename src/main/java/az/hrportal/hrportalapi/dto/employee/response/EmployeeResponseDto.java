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
    String foreignPassportSeries;
    String foreignPassportNumber;
    String foreignPassportStartDate;
    String foreignPassportEndDate;
    String IDCardSeries;
    String IDCardNumber;
    String IDCardPin;
    String IDCardOrganization;
    String IDCardStartDate;
    String IDCardEndDate;
    String addressCountry;
    String addressCity;
    String addressDistrict;
    String addressVillage;
    String addressStreet;
    String addressBlock;
    String addressApartment;
    String addressHome;
    String homePhone;
    String mobilePhone1;
    String mobilePhone2;
    String businessPhone;
    String internalBusinessPhone;
    String ownMailAddress;
    String businessMailAddress;
    List<FamilyMemberResponseDto> familyMembers;
    List<String> quotas;
    String company;
    String section;
    String subSection;
    String businessPosition;
    String jobStartDate;
    String jobEndDate;
    String jobEndReason; //TODO hardan gelir ??
    boolean isMainJob;
    String academicDegreeDate;
    String academicDegreeNumber;
    String academicDegreeOrganization;
    String institution; //TODO hardan gelir ??
    String faculty;
    String direction;
    String speciality;
    String entranceDate;
    String graduateDate;
    String degree; //TODO Enum or keep  ??
    String graduateFileNumber;
    String graduateFileDate;
    String educationType;
    String nostrifikasiyaNumber;
    String kvota;
    List<CertificateResponseDto> certificates;
    List<GovernmentAchievementResponseDto> governmentAchievements;
//    Position position;
    String driverCardCategory;
    String driverCardEndDate;
    boolean prisoner;
    boolean memberOfColleaguesAlliance;
}
