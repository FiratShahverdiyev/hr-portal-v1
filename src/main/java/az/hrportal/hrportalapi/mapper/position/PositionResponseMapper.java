package az.hrportal.hrportalapi.mapper.position;

import az.hrportal.hrportalapi.domain.position.Position;
import az.hrportal.hrportalapi.dto.position.response.GeneralInfoResponseDto;
import az.hrportal.hrportalapi.dto.position.response.KnowledgeResponseDto;
import az.hrportal.hrportalapi.dto.position.response.PositionResponseDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface PositionResponseMapper {
    @Mapping(target = "institutionName", source = "institution.name")
    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "subDepartmentName", source = "subDepartment.name")
    @Mapping(target = "vacancyName", source = "vacancy.name")
    @Mapping(target = "educationSpeciality", source = "educationSpeciality.name")
    @Mapping(target = "jobFamily", source = "jobFamily.name")
    @Mapping(target = "salary", source = "salary.salary")
    GeneralInfoResponseDto toGeneralInfoResponseDto(Position position);

    KnowledgeResponseDto toKnowledgeResponseDto(Position position);

    @IterableMapping(qualifiedByName = "toPositionResponseDto")
    List<PositionResponseDto> toPositionResponseDtos(List<Position> positions);

    @Named("toPositionResponseDto")
    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "subDepartmentName", source = "subDepartment.name")
    @Mapping(target = "vacancyName", source = "vacancy.name")
    @Mapping(target = "vacancyCount", source = "count")
    @Mapping(target = "salary", source = "salary.salary")
    PositionResponseDto toPositionResponseDto(Position position);
}