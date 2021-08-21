package az.hrportal.hrportalapi.mapper;

import az.hrportal.hrportalapi.domain.employee.Country;
import az.hrportal.hrportalapi.domain.position.Department;
import az.hrportal.hrportalapi.domain.position.Institution;
import az.hrportal.hrportalapi.domain.position.JobFamily;
import az.hrportal.hrportalapi.domain.position.Salary;
import az.hrportal.hrportalapi.domain.position.SubDepartment;
import az.hrportal.hrportalapi.domain.position.Vacancy;
import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface DropDownMapper {
    List<DropDownResponseDto> toCountryResponseDtos(List<Country> countries);

    List<DropDownResponseDto> toInstitutionResponseDtos(List<Institution> institutions);

    List<DropDownResponseDto> toDepartmentResponseDtos(List<Department> departments);

    List<DropDownResponseDto> toVacancyResponseDtos(List<Vacancy> vacancies);

    List<DropDownResponseDto> toSubDepartmentResponseDtos(List<SubDepartment> subDepartments);

    List<DropDownResponseDto> toSalaryResponseDtos(List<Salary> salaries);

    List<DropDownResponseDto> toJobFamilyResponseDtos(List<JobFamily> jobFamilies);
}
