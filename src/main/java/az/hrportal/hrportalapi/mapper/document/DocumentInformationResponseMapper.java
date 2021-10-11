package az.hrportal.hrportalapi.mapper.document;

import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.position.Position;
import az.hrportal.hrportalapi.dto.document.EmployeeDocumentInformation;
import az.hrportal.hrportalapi.dto.document.PositionDocumentInformation;
import az.hrportal.hrportalapi.mapper.position.helper.PositionMapperHelper;
import az.hrportal.hrportalapi.mapper.position.helper.VacancyCategoryValue;
import az.hrportal.hrportalapi.mapper.position.helper.WorkModeValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        componentModel = "spring", uses = PositionMapperHelper.class)
public interface DocumentInformationResponseMapper {
    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "subDepartmentName", source = "subDepartment.name")
    @Mapping(target = "vacancyName", source = "vacancy.name")
    @Mapping(target = "vacancyCount", source = "count")
    @Mapping(target = "salary", source = "salary.salary")
    @Mapping(target = "workMode", source = "workMode", qualifiedBy = WorkModeValue.class)
    @Mapping(target = "vacancyCategory", source = "vacancyCategory", qualifiedBy = VacancyCategoryValue.class)
    PositionDocumentInformation toPositionDocumentInformation(Position position);

    @Mapping(target = "departmentName", source = "position.department.name")
    @Mapping(target = "subDepartmentName", source = "position.subDepartment.name")
    @Mapping(target = "vacancyName", source = "position.vacancy.name")
    @Mapping(target = "salary", source = "position.salary.salary")
    @Mapping(target = "additionalSalary", source = "position.additionalSalary")
    @Mapping(target = "workMode", source = "position.workMode", qualifiedBy = WorkModeValue.class)
    EmployeeDocumentInformation toEmployeeDocumentInformation(Employee employee);
}
