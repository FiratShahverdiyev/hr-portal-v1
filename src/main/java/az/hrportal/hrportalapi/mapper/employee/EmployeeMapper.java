package az.hrportal.hrportalapi.mapper.employee;

import az.hrportal.hrportalapi.constant.employee.EducationType;
import az.hrportal.hrportalapi.constant.employee.Series;
import az.hrportal.hrportalapi.domain.employee.Address;
import az.hrportal.hrportalapi.domain.employee.Business;
import az.hrportal.hrportalapi.domain.employee.ContactInfo;
import az.hrportal.hrportalapi.domain.employee.Education;
import az.hrportal.hrportalapi.domain.employee.ForeignPassport;
import az.hrportal.hrportalapi.domain.employee.IDCard;
import az.hrportal.hrportalapi.dto.employee.request.AddressRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.BusinessRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.ContactInfoRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.EducationRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.ForeignPassportRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.IDCardRequestDto;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.text.SimpleDateFormat;
import java.util.Date;

//TODO Delete on production
@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface EmployeeMapper {
    Address toAddress(AddressRequestDto addressRequestDto);

    ContactInfo toContactInfo(ContactInfoRequestDto contactInfoRequestDto);

    @Mapping(target = "jobEndDate", source = "jobEndDate", qualifiedByName = "stringToDate",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "jobStartDate", source = "jobStartDate", qualifiedByName = "stringToDate",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Business toBusiness(BusinessRequestDto businessRequestDto);

    @Mapping(target = "academicDegreeDate", source = "academicDegreeDate", qualifiedByName = "stringToDate",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "entranceDate", source = "entranceDate", qualifiedByName = "stringToDate",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "graduateDate", source = "graduateDate", qualifiedByName = "stringToDate",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "graduateFileDate", source = "graduateFileDate", qualifiedByName = "stringToDate",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "educationType", source = "educationType", qualifiedByName = "intToEducationType",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Education toEducation(EducationRequestDto educationRequestDto);

    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "stringToDate",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "endDate", source = "endDate", qualifiedByName = "stringToDate",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "series", source = "series", qualifiedByName = "intToSeries",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    ForeignPassport toForeignPassport(ForeignPassportRequestDto foreignPassportRequestDto);

    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "stringToDate",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "endDate", source = "endDate", qualifiedByName = "stringToDate",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "series", source = "series", qualifiedByName = "intToSeries",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    IDCard toIdCard(IDCardRequestDto idCardRequestDto);

    @Named("stringToDate")
    @SneakyThrows
    default Date stringToDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.parse(date);
    }

    @Named("dateToString")
    @SneakyThrows
    default String dateToString(Date date) {
        return date.toString();
    }

    @Named("intToEducationType")
    default EducationType intToEducationType(Integer value) {
        return EducationType.intToEnum(value);
    }

    @Named("intToSeries")
    default Series intToSeries(Integer value) {
        return Series.intToEnum(value);
    }
}
