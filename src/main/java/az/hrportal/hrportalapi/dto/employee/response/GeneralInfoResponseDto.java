package az.hrportal.hrportalapi.dto.employee.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GeneralInfoResponseDto {
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
}
