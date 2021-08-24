package az.hrportal.hrportalapi.mapper.employee;

import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.dto.employee.response.AcademicInfoResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.BusinessResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.EmployeeResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.GeneralInfoResponseDto;
import az.hrportal.hrportalapi.mapper.employee.helper.CertificatesToDto;
import az.hrportal.hrportalapi.mapper.employee.helper.EmployeeMapperHelper;
import az.hrportal.hrportalapi.mapper.employee.helper.FamilyMembersToDto;
import az.hrportal.hrportalapi.mapper.employee.helper.GovernmentAchievementToDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = EmployeeMapperHelper.class)
public interface EmployeeResponseMapper {
    @Mapping(target = "academicDegreeDate", source = "academicDegreeDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "entranceDate", source = "entranceDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "graduateDate", source = "graduateDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "graduateFileDate", source = "graduateFileDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "driverCardEndDate", source = "driverCardEndDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "certificates", source = "certificates", qualifiedBy = CertificatesToDto.class,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "governmentAchievements", source = "governmentAchievements",
            qualifiedBy = GovernmentAchievementToDto.class, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    AcademicInfoResponseDto toAcademicInfoResponseDto(Employee employee);

    @Mapping(target = "IDCardStartDate", source = "IDCardStartDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "IDCardEndDate", source = "IDCardEndDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "foreignPassportStartDate", source = "foreignPassportStartDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "foreignPassportEndDate", source = "foreignPassportEndDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "birthday", source = "birthday", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "citizenCountry", source = "citizenCountry.name")
    @Mapping(target = "familyMembers", source = "familyMembers", qualifiedBy = FamilyMembersToDto.class,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    GeneralInfoResponseDto toGeneralInfoResponseDto(Employee employee);

    @Mapping(target = "jobStartDate", source = "jobStartDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "jobEndDate", source = "jobEndDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "position", source = "businessPosition")
    BusinessResponseDto toBusinessResponseDto(Employee employee);

    @IterableMapping(qualifiedByName = "toEmployeeResponseDto")
    List<EmployeeResponseDto> toEmployeeResponseDtos(List<Employee> employees);

    @Named("toEmployeeResponseDto")
    @Mapping(target = "position", source = "businessPosition")
    EmployeeResponseDto toEmployeeResponseDto(Employee employee);
}
