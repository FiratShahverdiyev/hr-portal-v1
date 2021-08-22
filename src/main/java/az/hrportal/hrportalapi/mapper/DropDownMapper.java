package az.hrportal.hrportalapi.mapper;

import az.hrportal.hrportalapi.domain.employee.Country;
import az.hrportal.hrportalapi.domain.position.Department;
import az.hrportal.hrportalapi.domain.position.Institution;
import az.hrportal.hrportalapi.domain.position.JobFamily;
import az.hrportal.hrportalapi.domain.position.Salary;
import az.hrportal.hrportalapi.domain.position.SubDepartment;
import az.hrportal.hrportalapi.domain.position.Vacancy;
import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface DropDownMapper {
    List<DropDownResponseDto<String>> toCountryResponseDtos(List<Country> countries);

    List<DropDownResponseDto<String>> toInstitutionResponseDtos(List<Institution> institutions);

    List<DropDownResponseDto<String>> toDepartmentResponseDtos(List<Department> departments);

    List<DropDownResponseDto<String>> toVacancyResponseDtos(List<Vacancy> vacancies);

    List<DropDownResponseDto<String>> toSubDepartmentResponseDtos(List<SubDepartment> subDepartments);

    @IterableMapping(qualifiedByName = "toSalaryResponseDto")
    List<DropDownResponseDto<BigDecimal>> toSalaryResponseDtos(List<Salary> salaries);

    @Named("toSalaryResponseDto")
    @Mapping(target = "name", source = "salary")
    DropDownResponseDto<BigDecimal> toSalaryResponseDto(Salary salary);

    List<DropDownResponseDto<String>> toJobFamilyResponseDtos(List<JobFamily> jobFamilies);
}
