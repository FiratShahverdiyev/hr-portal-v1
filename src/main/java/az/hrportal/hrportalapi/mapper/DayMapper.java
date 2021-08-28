package az.hrportal.hrportalapi.mapper;

import az.hrportal.hrportalapi.domain.Day;
import az.hrportal.hrportalapi.dto.DayResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface DayMapper {
    DayResponseDto toDayResponseDto(Day day);
}
