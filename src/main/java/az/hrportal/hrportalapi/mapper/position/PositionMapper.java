package az.hrportal.hrportalapi.mapper.position;

import az.hrportal.hrportalapi.domain.position.Position;
import az.hrportal.hrportalapi.dto.position.request.GeneralInfoRequestDto;
import az.hrportal.hrportalapi.dto.position.request.KnowledgeRequestDto;
import az.hrportal.hrportalapi.mapper.position.helper.IntToWorkPlace;
import az.hrportal.hrportalapi.mapper.position.helper.PositionMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = PositionMapperHelper.class)
public interface PositionMapper {
    @Mapping(target = "jobFamily", source = "jobFamily", ignore = true)
    @Mapping(target = "skills", source = "skills", ignore = true)
    @Mapping(target = "educationSpeciality", source = "educationSpeciality", ignore = true)
    @Mapping(target = "workPlace", source = "workPlace", qualifiedBy = IntToWorkPlace.class)
    @Mapping(target = "salary", source = "salary", ignore = true)
    @Mapping(target = "count", source = "vacancyCount")
    void updatePosition(@MappingTarget Position position, GeneralInfoRequestDto generalInfoRequestDto);

    void updatePosition(@MappingTarget Position position, KnowledgeRequestDto knowledgeRequestDto);
}
