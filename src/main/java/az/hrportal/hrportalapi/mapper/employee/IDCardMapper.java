package az.hrportal.hrportalapi.mapper.employee;

import az.hrportal.hrportalapi.domain.employee.IDCard;
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
public interface IDCardMapper {
    @Mapping(target = "series", source = "IDCardSeries")
    @Mapping(target = "startDate", source = "IDCardStartDate", qualifiedByName = "stringToDate")
    @Mapping(target = "endDate", source = "IDCardEndDate", qualifiedByName = "stringToDate")
    @Mapping(target = "number", source = "IDCardNumber")
    @Mapping(target = "pin", source = "IDCardPin")
    @Mapping(target = "organization", source = "IDCardOrganization")
    IDCard toIdCard(EmployeeGeneralInfoRequestDto generalInfoRequestDto);

    @Named("stringToDate")
    @SneakyThrows
    default Date stringToDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.parse(date);
    }
}
