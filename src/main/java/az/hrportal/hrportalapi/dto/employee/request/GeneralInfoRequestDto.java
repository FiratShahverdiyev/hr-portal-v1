package az.hrportal.hrportalapi.dto.employee.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GeneralInfoRequestDto {
    @NotNull
    Integer IDCardSeries;
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

    @NotNull
    Integer foreignPassportSeries;
    @NotEmpty
    String foreignPassportStartDate;
    @NotEmpty
    String foreignPassportEndDate;
    @NotEmpty
    String foreignPassportNumber;

    @NotEmpty
    String addressCountry;
    @NotEmpty
    String addressCity;
    @NotEmpty
    String addressDistrict;
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
    @NotEmpty
    String ownMailAddress;
    @NotEmpty
    String businessMailAddress;

    Integer familyCondition;
    @NotEmpty
    String fullName;
    @NotEmpty
    String birthday;
    @NotEmpty
    String birthplace;
    @NotEmpty
    String citizenCountry;
    @NotNull
    Integer gender;
    Integer bloodGroup;
    String permission;

    List<FamilyMemberRequestDto> familyMembers;
}
