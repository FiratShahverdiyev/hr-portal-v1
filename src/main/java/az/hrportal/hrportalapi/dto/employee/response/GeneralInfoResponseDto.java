package az.hrportal.hrportalapi.dto.employee.response;

import az.hrportal.hrportalapi.dto.KeyValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GeneralInfoResponseDto {
    Integer id;

    String IDCardSeries;
    String IDCardNumber;
    String IDCardPin;
    String IDCardStartDate;
    String IDCardEndDate;
    String IDCardOrganization;

    String familyCondition;
    String fullName;
    String birthday;
    String birthplace;
    String citizenCountry; // ?
    String gender;
    String bloodGroup;
    String permission;

    String foreignPassportSeries;
    String foreignPassportStartDate;
    String foreignPassportEndDate;
    String foreignPassportNumber;

    KeyValue<String, Integer> addressCountry;
    KeyValue<String, Integer> addressCity;
    KeyValue<String, Integer> addressDistrict;
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

    String workPermissionSerial;
    String workPermissionNumber;
    Integer workPermissionPeriod;
    String startWorkPermissionDate;
    String expiredWorkPermissionDate;

    String employeeActivity;
    List<FamilyMemberResponseDto> familyMembers;
    String photo;
}
