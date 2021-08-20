package az.hrportal.hrportalapi.dto.employee;

import az.hrportal.hrportalapi.constant.employee.DriverCategory;
import az.hrportal.hrportalapi.domain.embeddable.Certificate;
import az.hrportal.hrportalapi.domain.embeddable.FamilyMember;
import az.hrportal.hrportalapi.domain.employee.Address;
import az.hrportal.hrportalapi.domain.employee.Business;
import az.hrportal.hrportalapi.domain.employee.ContactInfo;
import az.hrportal.hrportalapi.domain.employee.Country;
import az.hrportal.hrportalapi.domain.employee.Education;
import az.hrportal.hrportalapi.domain.employee.ForeignPassport;
import az.hrportal.hrportalapi.domain.employee.GovernmentAchievement;
import az.hrportal.hrportalapi.domain.employee.IDCard;
import az.hrportal.hrportalapi.domain.position.Position;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeUpdateRequestDto {
    String photo;
    Integer familyCondition;
    Integer militaryAchievement;

    String fullName;
    String birthDay;
    String birthPlace;
    Country citizenCountry;
    Integer gender;
    Integer bloodGroup;
    String permission;

    ForeignPassport foreignPassport;
    IDCard idCard;
    Address address;
    ContactInfo contactInfo;

    List<FamilyMember> familyMembers;

    List<String> quotas;

    Business business;

    Education education;

    String kvota;

    List<Certificate> certificates;
    List<GovernmentAchievement> governmentAchievements;

    List<Position> positions;

    DriverCategory driverCardCategory;
    Date driverCardEndDate;

    boolean isPrisoner;
    boolean isMemberOfColleaguesAlliance;
}
