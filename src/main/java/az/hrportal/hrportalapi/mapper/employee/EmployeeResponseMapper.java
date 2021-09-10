package az.hrportal.hrportalapi.mapper.employee;

import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.dto.employee.response.AcademicInfoResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.BusinessResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.EmployeeResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.GeneralInfoResponseDto;
import az.hrportal.hrportalapi.mapper.employee.helper.BloodGroupValue;
import az.hrportal.hrportalapi.mapper.employee.helper.CertificatesToDto;
import az.hrportal.hrportalapi.mapper.employee.helper.EducationTypeValue;
import az.hrportal.hrportalapi.mapper.employee.helper.EmployeeMapperHelper;
import az.hrportal.hrportalapi.mapper.employee.helper.FamilyConditionValue;
import az.hrportal.hrportalapi.mapper.employee.helper.FamilyMembersToDto;
import az.hrportal.hrportalapi.mapper.employee.helper.GenderValue;
import az.hrportal.hrportalapi.mapper.employee.helper.GovernmentAchievementToDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static az.hrportal.hrportalapi.constant.Constant.dateFormat;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = EmployeeMapperHelper.class)
public interface EmployeeResponseMapper {
    @Mapping(target = "academicDegreeDate", source = "academicDegreeDate", dateFormat = dateFormat)
    @Mapping(target = "entranceDate", source = "entranceDate", dateFormat = dateFormat)
    @Mapping(target = "graduateDate", source = "graduateDate", dateFormat = dateFormat)
    @Mapping(target = "graduateFileDate", source = "graduateFileDate", dateFormat = dateFormat)
    @Mapping(target = "driverCardEndDate", source = "driverCardEndDate", dateFormat = dateFormat)
    @Mapping(target = "educationType", source = "educationType", qualifiedBy = EducationTypeValue.class,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "certificates", source = "certificates", qualifiedBy = CertificatesToDto.class,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "governmentAchievements", source = "governmentAchievements",
            qualifiedBy = GovernmentAchievementToDto.class, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    AcademicInfoResponseDto toAcademicInfoResponseDto(Employee employee);

    @Mapping(target = "IDCardStartDate", source = "IDCardStartDate", dateFormat = dateFormat)
    @Mapping(target = "IDCardEndDate", source = "IDCardEndDate", dateFormat = dateFormat)
    @Mapping(target = "foreignPassportStartDate", source = "foreignPassportStartDate", dateFormat = dateFormat)
    @Mapping(target = "foreignPassportEndDate", source = "foreignPassportEndDate", dateFormat = dateFormat)
    @Mapping(target = "birthday", source = "birthday", dateFormat = dateFormat)
    @Mapping(target = "citizenCountry", source = "citizenCountry.name")
    @Mapping(target = "addressCountry", source = "addressCountry.name")
    @Mapping(target = "addressCity", source = "addressCity.name")
    @Mapping(target = "addressDistrict", source = "addressDistrict.name")
    @Mapping(target = "familyMembers", source = "familyMembers", qualifiedBy = FamilyMembersToDto.class,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "familyCondition", source = "familyCondition", qualifiedBy = FamilyConditionValue.class)
    @Mapping(target = "gender", source = "gender", qualifiedBy = GenderValue.class)
    @Mapping(target = "bloodGroup", source = "bloodGroup", qualifiedBy = BloodGroupValue.class)
    GeneralInfoResponseDto toGeneralInfoResponseDto(Employee employee);

    @Mapping(target = "jobStartDate", source = "jobStartDate", dateFormat = dateFormat)
    @Mapping(target = "jobEndDate", source = "jobEndDate", dateFormat = dateFormat)
    @Mapping(target = "position", source = "businessPosition")
    BusinessResponseDto toBusinessResponseDto(Employee employee);

    @IterableMapping(qualifiedByName = "toEmployeeResponseDto")
    List<EmployeeResponseDto> toEmployeeResponseDtos(List<Employee> employees);

    @Named("toEmployeeResponseDto")
    @Mapping(target = "position", source = "position.vacancy.name")
    @Mapping(target = "department", source = "position.department.name")
    EmployeeResponseDto toEmployeeResponseDto(Employee employee);
}
