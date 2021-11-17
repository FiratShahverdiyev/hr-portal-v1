package az.hrportal.hrportalapi.mapper;

import az.hrportal.hrportalapi.domain.employee.CitizenCountry;
import az.hrportal.hrportalapi.domain.employee.GovernmentAchievement;
import az.hrportal.hrportalapi.domain.position.Department;
import az.hrportal.hrportalapi.domain.position.EducationInstitution;
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

import java.util.List;
import java.util.Set;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface DropDownMapper {
    List<DropDownResponseDto<String>> toCountryResponseDtos(List<CitizenCountry> countries);

    List<DropDownResponseDto<String>> toInstitutionResponseDtos(List<Institution> institutions);

    List<DropDownResponseDto<String>> toEducationInstitutionResponseDtos(List<EducationInstitution> educationInstitutions);

    List<DropDownResponseDto<String>> toDepartmentResponseDtos(List<Department> departments);

    List<DropDownResponseDto<String>> toVacancyResponseDtos(List<Vacancy> vacancies);

    List<DropDownResponseDto<String>> toSubDepartmentResponseDtos(Set<SubDepartment> subDepartments);

    @IterableMapping(qualifiedByName = "toSalaryResponseDto")
    List<DropDownResponseDto<Float>> toSalaryResponseDtos(List<Salary> salaries);

    @Named("toSalaryResponseDto")
    @Mapping(target = "name", source = "amount")
    DropDownResponseDto<Float> toSalaryResponseDto(Salary salary);

    List<DropDownResponseDto<String>> toJobFamilyResponseDtos(List<JobFamily> jobFamilies);

    List<DropDownResponseDto<String>> toGovernmentAchievementDtos(List<GovernmentAchievement> governmentAchievements);
}
