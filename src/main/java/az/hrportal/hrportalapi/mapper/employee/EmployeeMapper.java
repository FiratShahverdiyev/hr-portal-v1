package az.hrportal.hrportalapi.mapper.employee;

import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.dto.employee.request.AcademicRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.BusinessRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.EmployeeGeneralInfoRequestDto;
import az.hrportal.hrportalapi.mapper.employee.helper.DtoToCertificates;
import az.hrportal.hrportalapi.mapper.employee.helper.DtoToFamilyMembers;
import az.hrportal.hrportalapi.mapper.employee.helper.EmployeeMapperHelper;
import org.mapstruct.*;

import static az.hrportal.hrportalapi.constant.Constant.dateFormat;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = EmployeeMapperHelper.class)
public interface EmployeeMapper {
    @Mapping(target = "birthday", source = "birthday", dateFormat = dateFormat)
    @Mapping(target = "familyMembers", source = "familyMembers", qualifiedBy = DtoToFamilyMembers.class)
    @Mapping(target = "foreignPassportStartDate", source = "foreignPassportStartDate", dateFormat = dateFormat)
    @Mapping(target = "IDCardStartDate", source = "IDCardStartDate", dateFormat = dateFormat)
    @Mapping(target = "foreignPassportEndDate", source = "foreignPassportEndDate", dateFormat = dateFormat)
    @Mapping(target = "IDCardEndDate", source = "IDCardEndDate", dateFormat = dateFormat)
    @Mapping(target = "startWorkPermissionDate", source = "startWorkPermissionDate", dateFormat = dateFormat)
    @Mapping(target = "expiredWorkPermissionDate", source = "expiredWorkPermissionDate", dateFormat = dateFormat)
    @Mapping(target = "citizenCountry", source = "citizenCountry", ignore = true)
    void updateEmployee(@MappingTarget Employee employee, EmployeeGeneralInfoRequestDto generalInfoRequestDto);

    @Mapping(target = "jobStartDate", source = "jobStartDate", dateFormat = dateFormat)
    @Mapping(target = "jobEndDate", source = "jobEndDate", dateFormat = dateFormat)
    @Mapping(target = "position", source = "position", ignore = true)
    void updateEmployee(@MappingTarget Employee employee, BusinessRequestDto businessRequestDto);

    @Mapping(target = "academicDegreeDate", source = "academicDegreeDate", dateFormat = dateFormat)
    @Mapping(target = "entranceDate", source = "entranceDate", dateFormat = dateFormat)
    @Mapping(target = "graduateDate", source = "graduateDate", dateFormat = dateFormat)
    @Mapping(target = "graduateFileDate", source = "graduateFileDate", dateFormat = dateFormat)
    @Mapping(target = "driverCardEndDate", source = "driverCardEndDate", dateFormat = dateFormat)
    @Mapping(target = "certificates", source = "certificates", qualifiedBy = DtoToCertificates.class,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "governmentAchievements", source = "governmentAchievements", ignore = true)
    @Mapping(target = "quotas", source = "quotas", ignore = true)
    void updateEmployee(@MappingTarget Employee employee, AcademicRequestDto academicRequestDto);
}
