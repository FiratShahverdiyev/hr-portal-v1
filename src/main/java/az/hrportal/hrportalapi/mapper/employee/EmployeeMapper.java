package az.hrportal.hrportalapi.mapper.employee;

import az.hrportal.hrportalapi.domain.embeddable.FamilyMember;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.dto.employee.request.EmployeeGeneralInfoRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.FamilyMemberRequestDto;
import az.hrportal.hrportalapi.dto.employee.response.EmployeeResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.FamilyMemberResponseDto;
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
public interface EmployeeMapper {
    @Mapping(target = "birthday", source = "birthday", qualifiedByName = "stringToDate",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "familyMembers", source = "familyMembers", qualifiedByName = "toFamilyMembers")
    @Mapping(target = "citizenCountry", source = "citizenCountry", ignore = true)
    Employee toEmployee(EmployeeGeneralInfoRequestDto generalInfoRequestDto);

    @IterableMapping(qualifiedByName = "toEmployeeResponseDto")
    List<EmployeeResponseDto> toEmployeeResponseDtos(List<Employee> employees);

    @Named("toEmployeeResponseDto")
    @Mapping(target = "birthday", source = "birthday", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "foreignPassport.startDate", source = "foreignPassport.startDate",
            qualifiedByName = "dateToString", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "foreignPassport.endDate", source = "foreignPassport.endDate", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "idCard.startDate", source = "idCard.startDate", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "idCard.endDate", source = "idCard.endDate", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "familyMembers", source = "familyMembers", qualifiedByName = "toFamilyMemberResponseDtos")
    @Mapping(target = "business.jobEndDate", source = "business.jobEndDate", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "business.jobStartDate", source = "business.jobStartDate", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "education.academicDegreeDate", source = "education.academicDegreeDate",
            qualifiedByName = "dateToString", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "education.entranceDate", source = "education.entranceDate", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "education.graduateDate", source = "education.graduateDate", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "education.graduateFileDate", source = "education.graduateFileDate",
            qualifiedByName = "dateToString", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "citizenCountry", source = "citizenCountry.name")
    EmployeeResponseDto toEmployeeResponseDto(Employee employee);

    @Named("stringToDate")
    @SneakyThrows
    default Date stringToDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.parse(date);
    }

    @Named("dateToString")
    @SneakyThrows
    default String dateToString(Date date) {
        return String.valueOf(date);
    }

    @Named("toFamilyMembers")
    @IterableMapping(qualifiedByName = "toFamilyMember")
    Set<FamilyMember> toFamilyMembers(List<FamilyMemberRequestDto> familyMemberRequestDtos);

    @Named("toFamilyMember")
    @Mapping(target = "birthday", source = "birthday", qualifiedByName = "stringToDate",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    FamilyMember toFamilyMember(FamilyMemberRequestDto familyMemberRequestDto);

    @Named("toFamilyMemberResponseDtos")
    @IterableMapping(qualifiedByName = "toFamilyMemberResponseDto")
    List<FamilyMemberResponseDto> toFamilyMemberResponseDtos(Set<FamilyMember> familyMembers);

    @Named("toFamilyMemberResponseDto")
    @Mapping(target = "birthday", source = "birthday", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    FamilyMemberResponseDto toFamilyMemberResponseDto(FamilyMember familyMember);
}
