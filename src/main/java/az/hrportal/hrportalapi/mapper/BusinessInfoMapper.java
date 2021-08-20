package az.hrportal.hrportalapi.mapper;

import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.dto.employee.response.BusinessResponseDto;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Date;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface BusinessInfoMapper {
    @Mapping(target = "company", source = "business.company")
    @Mapping(target = "section", source = "business.section")
    @Mapping(target = "subSection", source = "business.subSection")
    @Mapping(target = "position", source = "business.position")
    @Mapping(target = "jobStartDate", source = "business.jobStartDate", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "jobEndDate", source = "business.jobEndDate", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "jobEndReason", source = "business.jobEndReason")
    @Mapping(target = "mainJob", source = "business.mainJob")
    BusinessResponseDto toBusinessResponseDto(Employee employee);

    @Named("dateToString")
    @SneakyThrows
    default String dateToString(Date date) {
        return date.toString();
    }
}
