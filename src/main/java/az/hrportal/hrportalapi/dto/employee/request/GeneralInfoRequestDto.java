package az.hrportal.hrportalapi.dto.employee.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GeneralInfoRequestDto {
    @NotNull
    @Range(min = 1, max = 1)
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
    @Range(min = 1, max = 1)
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
    @Range(min = 1, max = 4)
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
    @Range(min = 1, max = 2)
    Integer gender;
    @Range(min = 1, max = 4)
    Integer bloodGroup;
    String permission;

    List<FamilyMemberRequestDto> familyMembers;
}
