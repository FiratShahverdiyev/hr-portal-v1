package az.hrportal.hrportalapi.mapper.employee;

import az.hrportal.hrportalapi.domain.employee.EmployeeSalary;
import az.hrportal.hrportalapi.dto.employee.response.EmployeeSalaryResponseDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        componentModel = "spring")
public interface EmployeeSalaryMapper {

    @IterableMapping(qualifiedByName = "toEmployeeSalaryResponseDto")
    List<EmployeeSalaryResponseDto> toEmployeeSalaryResponseDtos(List<EmployeeSalary> employeeSalaries);

    @Named("toEmployeeSalaryResponseDto")
    @Mapping(target = "id", source = "employee.id")
    @Mapping(target = "fullName", source = "employee.fullName")
    @Mapping(target = "vacancyName", source = "employee.position.vacancy.name")
    EmployeeSalaryResponseDto toEmployeeSalaryResponseDto(EmployeeSalary employeeSalary);
}
