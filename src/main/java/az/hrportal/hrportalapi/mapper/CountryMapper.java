package az.hrportal.hrportalapi.mapper;

import az.hrportal.hrportalapi.domain.employee.Country;
import az.hrportal.hrportalapi.dto.employee.response.CountryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface CountryMapper {
    List<CountryResponseDto> toCountryResponseDtos(List<Country> countries);
}
