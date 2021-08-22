package az.hrportal.hrportalapi.mapper.employee;

import az.hrportal.hrportalapi.domain.employee.ForeignPassport;
import az.hrportal.hrportalapi.dto.employee.request.EmployeeGeneralInfoRequestDto;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface ForeignPassportMapper {
    @Mapping(target = "series", source = "foreignPassportSeries")
    @Mapping(target = "startDate", source = "foreignPassportStartDate", qualifiedByName = "stringToDate")
    @Mapping(target = "endDate", source = "foreignPassportEndDate", qualifiedByName = "stringToDate")
    @Mapping(target = "number", source = "foreignPassportNumber")
    ForeignPassport toForeignPassport(EmployeeGeneralInfoRequestDto generalInfoRequestDto);

    @Named("stringToDate")
    @SneakyThrows
    default Date stringToDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.parse(date);
    }
}
