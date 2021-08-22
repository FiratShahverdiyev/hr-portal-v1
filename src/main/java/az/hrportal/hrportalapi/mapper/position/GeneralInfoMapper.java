package az.hrportal.hrportalapi.mapper.position;

import az.hrportal.hrportalapi.domain.position.Position;
import az.hrportal.hrportalapi.dto.position.response.GeneralInfoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface GeneralInfoMapper {
    @Mapping(target = "institutionName", source = "institution.name")
    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "subDepartmentName", source = "subDepartment.name")
    @Mapping(target = "vacancyName", source = "vacancy.name")
    @Mapping(target = "educationSpeciality", source = "educationSpeciality.name")
    @Mapping(target = "jobFamily", source = "jobFamily.name")
    GeneralInfoResponseDto toGeneralInfoResponseDto(Position position);
}
