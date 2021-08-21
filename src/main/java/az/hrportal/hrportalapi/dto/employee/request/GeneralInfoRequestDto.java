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
public class GeneralInfoRequestDto {
    Integer IDCardSeries;
    String IDCardNumber;
    String IDCardPin;
    String IDCardStartDate;
    String IDCardEndDate;
    String IDCardOrganization;

    Integer foreignPassportSeries;
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

    Integer familyCondition;
    String fullName;
    String birthday;
    String birthplace;
    Integer citizenCountryId; // ?
    Integer gender;
    Integer bloodGroup;
    String permission;

    List<FamilyMemberRequestDto> familyMembers;
}
