package az.hrportal.hrportalapi.mapper;

import az.hrportal.hrportalapi.constant.employee.BloodGroup;
import az.hrportal.hrportalapi.constant.employee.EducationType;
import az.hrportal.hrportalapi.constant.employee.FamilyCondition;
import az.hrportal.hrportalapi.constant.employee.Gender;
import az.hrportal.hrportalapi.constant.employee.RelationType;
import az.hrportal.hrportalapi.constant.employee.Series;
import az.hrportal.hrportalapi.domain.embeddable.FamilyMember;
import az.hrportal.hrportalapi.domain.employee.Address;
import az.hrportal.hrportalapi.domain.employee.Business;
import az.hrportal.hrportalapi.domain.employee.ContactInfo;
import az.hrportal.hrportalapi.domain.employee.Education;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.employee.ForeignPassport;
import az.hrportal.hrportalapi.domain.employee.IDCard;
import az.hrportal.hrportalapi.dto.employee.request.AddressRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.BusinessRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.ContactInfoRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.EducationRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.ForeignPassportRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.IDCardRequestDto;
import az.hrportal.hrportalapi.dto.employee.response.FamilyMemberResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.GeneralInfoResponseDto;
import lombok.SneakyThrows;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface EmployeeMapper {
    Address toAddress(AddressRequestDto addressRequestDto);

    ContactInfo toContactInfo(ContactInfoRequestDto contactInfoRequestDto);

    @Mapping(target = "jobEndDate", source = "jobEndDate", qualifiedByName = "stringToDate")
    @Mapping(target = "jobStartDate", source = "jobStartDate", qualifiedByName = "stringToDate")
    Business toBusiness(BusinessRequestDto businessRequestDto);

    @Mapping(target = "academicDegreeDate", source = "academicDegreeDate", qualifiedByName = "stringToDate")
    @Mapping(target = "entranceDate", source = "entranceDate", qualifiedByName = "stringToDate")
    @Mapping(target = "graduateDate", source = "graduateDate", qualifiedByName = "stringToDate")
    @Mapping(target = "graduateFileDate", source = "graduateFileDate", qualifiedByName = "stringToDate")
    @Mapping(target = "educationType", source = "educationType", qualifiedByName = "intToEducationType")
    Education toEducation(EducationRequestDto educationRequestDto);

    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "stringToDate")
    @Mapping(target = "endDate", source = "endDate", qualifiedByName = "stringToDate")
    @Mapping(target = "series", source = "series", qualifiedByName = "intToSeries")
    ForeignPassport toForeignPassport(ForeignPassportRequestDto foreignPassportRequestDto);

    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "stringToDate")
    @Mapping(target = "endDate", source = "endDate", qualifiedByName = "stringToDate")
    @Mapping(target = "series", source = "series", qualifiedByName = "intToSeries")
    IDCard toIdCard(IDCardRequestDto idCardRequestDto);


    @Mapping(target = "IDCardSeries", source = "idCard.series")
    @Mapping(target = "IDCardNumber", source = "idCard.number")
    @Mapping(target = "IDCardStartDate", source = "idCard.startDate", qualifiedByName = "dateToString")
    @Mapping(target = "IDCardEndDate", source = "idCard.endDate", qualifiedByName = "dateToString")
    @Mapping(target = "IDCardOrganization", source = "idCard.organization")
    @Mapping(target = "IDCardPin", source = "idCard.pin")
    @Mapping(target = "foreignPassportSeries", source = "foreignPassport.series")
    @Mapping(target = "foreignPassportNumber", source = "foreignPassport.number")
    @Mapping(target = "foreignPassportStartDate", source = "foreignPassport.startDate",
            qualifiedByName = "dateToString")
    @Mapping(target = "foreignPassportEndDate", source = "foreignPassport.endDate", qualifiedByName = "dateToString")
    @Mapping(target = "addressCountry", source = "address.country")
    @Mapping(target = "addressCity", source = "address.city")
    @Mapping(target = "addressDistrict", source = "address.district")
    @Mapping(target = "addressVillage", source = "address.village")
    @Mapping(target = "addressStreet", source = "address.street")
    @Mapping(target = "addressBlock", source = "address.block")
    @Mapping(target = "addressApartment", source = "address.apartment")
    @Mapping(target = "addressHome", source = "address.home")
    @Mapping(target = "homePhone", source = "contactInfo.homePhone")
    @Mapping(target = "mobilePhone1", source = "contactInfo.mobilePhone1")
    @Mapping(target = "mobilePhone2", source = "contactInfo.mobilePhone2")
    @Mapping(target = "businessPhone", source = "contactInfo.businessPhone")
    @Mapping(target = "internalBusinessPhone", source = "contactInfo.internalBusinessPhone")
    @Mapping(target = "ownMailAddress", source = "contactInfo.ownMailAddress")
    @Mapping(target = "businessMailAddress", source = "contactInfo.businessMailAddress")
    @Mapping(target = "birthDay", source = "birthDay", qualifiedByName = "dateToString")
    @Mapping(target = "familyCondition", source = "familyCondition")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "bloodGroup", source = "bloodGroup")
    @Mapping(target = "familyMembers", source = "familyMembers", qualifiedByName = "toFamilyMemberResponseDtos")
    GeneralInfoResponseDto toGeneralInfoRequestDto(Employee employee);

    @Named("toFamilyMemberResponseDtos")
    @IterableMapping(qualifiedByName = "toFamilyMemberResponseDto")
    List<FamilyMemberResponseDto> toFamilyMemberResponseDtos(List<FamilyMember> familyMembers);

    @Named("toFamilyMemberResponseDto")
    @Mapping(target = "relationType", source = "relationType")
    @Mapping(target = "birthDay", source = "birthDay", qualifiedByName = "dateToString")
    FamilyMemberResponseDto toFamilyMemberResponseDto(FamilyMember familyMember);

    @Named("stringToDate")
    @SneakyThrows
    default Date stringToDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.parse(date);
    }

    @Named("dateToString")
    @SneakyThrows
    default String dateToString(Date date) {
        return date.toString();
    }

    @Named("intToEducationType")
    default EducationType intToEducationType(Integer value) {
        return EducationType.intToEnum(value);
    }

    @Named("intToSeries")
    default Series intToSeries(Integer value) {
        return Series.intToEnum(value);
    }

    @Named("seriesToInt")
    default Integer seriesToInt(Series series) {
        return series.getSeries();
    }

    @Named("familyConditionToInt")
    default Integer familyConditionToInt(FamilyCondition value) {
        return value.getCondition();
    }

    @Named("genderToInt")
    default Integer familyConditionToInt(Gender value) {
        return value.getGender();
    }

    @Named("bloodGroupToInt")
    default Integer familyConditionToInt(BloodGroup value) {
        return value.getBloodGroup();
    }

    @Named("relationTypeToInt")
    default Integer relationTypeToInt(RelationType value) {
        return value.getValue();
    }
}
