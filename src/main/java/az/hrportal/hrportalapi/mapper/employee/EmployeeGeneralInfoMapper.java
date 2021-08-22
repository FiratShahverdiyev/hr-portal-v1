package az.hrportal.hrportalapi.mapper.employee;

import az.hrportal.hrportalapi.domain.embeddable.FamilyMember;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.dto.employee.response.FamilyMemberResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.GeneralInfoResponseDto;
import lombok.SneakyThrows;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface EmployeeGeneralInfoMapper {
    @Mapping(target = "IDCardSeries", source = "idCard.series")
    @Mapping(target = "IDCardNumber", source = "idCard.number")
    @Mapping(target = "IDCardStartDate", source = "idCard.startDate", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "IDCardEndDate", source = "idCard.endDate", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "IDCardOrganization", source = "idCard.organization")
    @Mapping(target = "IDCardPin", source = "idCard.pin")
    @Mapping(target = "foreignPassportSeries", source = "foreignPassport.series")
    @Mapping(target = "foreignPassportNumber", source = "foreignPassport.number")
    @Mapping(target = "foreignPassportStartDate", source = "foreignPassport.startDate",
            qualifiedByName = "dateToString", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "foreignPassportEndDate", source = "foreignPassport.endDate", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
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
    @Mapping(target = "birthday", source = "birthday", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "citizenCountry", source = "citizenCountry.name")
    @Mapping(target = "familyMembers", source = "familyMembers", qualifiedByName = "toFamilyMemberResponseDtos",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    GeneralInfoResponseDto toGeneralInfoResponseDto(Employee employee);

    @Named("toFamilyMemberResponseDtos")
    @IterableMapping(qualifiedByName = "toFamilyMemberResponseDto")
    List<FamilyMemberResponseDto> toFamilyMemberResponseDtos(Set<FamilyMember> familyMembers);

    @Named("toFamilyMemberResponseDto")
    @Mapping(target = "birthday", source = "birthday", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
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
}
