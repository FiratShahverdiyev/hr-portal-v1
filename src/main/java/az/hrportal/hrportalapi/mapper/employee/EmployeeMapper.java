package az.hrportal.hrportalapi.mapper.employee;

import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.dto.employee.request.AcademicRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.BusinessRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.EmployeeGeneralInfoRequestDto;
import az.hrportal.hrportalapi.mapper.employee.helper.DtoToCertificates;
import az.hrportal.hrportalapi.mapper.employee.helper.DtoToFamilyMembers;
import az.hrportal.hrportalapi.mapper.employee.helper.EmployeeMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = EmployeeMapperHelper.class)
public interface EmployeeMapper {
    @Mapping(target = "birthday", source = "birthday", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "familyMembers", source = "familyMembers", qualifiedBy = DtoToFamilyMembers.class)
    @Mapping(target = "foreignPassportStartDate", source = "foreignPassportStartDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "IDCardStartDate", source = "IDCardStartDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "foreignPassportEndDate", source = "foreignPassportEndDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "IDCardEndDate", source = "IDCardEndDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "citizenCountry", source = "citizenCountry", ignore = true)
    void updateEmployee(@MappingTarget Employee employee, EmployeeGeneralInfoRequestDto generalInfoRequestDto);

    @Mapping(target = "jobStartDate", source = "jobStartDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "jobEndDate", source = "jobEndDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "position", source = "position", ignore = true)
    void updateEmployee(@MappingTarget Employee employee, BusinessRequestDto businessRequestDto);

    @Mapping(target = "academicDegreeDate", source = "academicDegreeDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "entranceDate", source = "entranceDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "graduateDate", source = "graduateDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "graduateFileDate", source = "graduateFileDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "driverCardEndDate", source = "driverCardEndDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "certificates", source = "certificates", qualifiedBy = DtoToCertificates.class,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "governmentAchievements", source = "governmentAchievements", ignore = true)
    @Mapping(target = "quotas", source = "quotas", ignore = true)
    void updateEmployee(@MappingTarget Employee employee, AcademicRequestDto academicRequestDto);
}
