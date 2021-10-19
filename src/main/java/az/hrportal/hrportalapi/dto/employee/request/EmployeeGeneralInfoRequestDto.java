package az.hrportal.hrportalapi.dto.employee.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeGeneralInfoRequestDto {
    @NotEmpty
    String IDCardSeries;
    @NotEmpty
    String IDCardNumber;
    @NotEmpty
    String IDCardPin;
    @NotEmpty
    String IDCardStartDate;
    @NotEmpty
    String IDCardEndDate;
    @NotEmpty
    String IDCardOrganization;

    @NotEmpty
    String foreignPassportSeries;
    @NotEmpty
    String foreignPassportStartDate;
    @NotEmpty
    String foreignPassportEndDate;
    @NotEmpty
    String foreignPassportNumber;

    @NotNull
    Integer addressCountryId;
    @NotNull
    Integer addressCityId;
    @NotNull
    Integer addressDistrictId;
    @NotEmpty
    String addressVillage;
    @NotEmpty
    String addressStreet;
    @NotEmpty
    String addressBlock;
    @NotEmpty
    String addressApartment;
    @NotEmpty
    String addressHome;

    @NotEmpty
    String homePhone;
    @NotEmpty
    String mobilePhone1;
    @NotEmpty
    String mobilePhone2;
    @NotEmpty
    String businessPhone;
    @NotEmpty
    String internalBusinessPhone;
    @Email
    @NotEmpty
    String ownMailAddress;
    @Email
    @NotEmpty
    String businessMailAddress;
    String familyCondition;
    @NotEmpty
    String fullName;
    @NotEmpty
    String birthday;
    @NotEmpty
    String birthplace;
    @NotEmpty
    String citizenCountry;
    @NotEmpty
    String gender;
    String bloodGroup;
    String permission;

    String workPermissionSerial;
    String workPermissionNumber;
    Integer workPermissionPeriod;
    String startWorkPermissionDate;
    String expiredWorkPermissionDate;

    List<FamilyMemberRequestDto> familyMembers;
}
