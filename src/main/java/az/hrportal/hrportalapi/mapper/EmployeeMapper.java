package az.hrportal.hrportalapi.mapper;

import az.hrportal.hrportalapi.constant.employee.EducationType;
import az.hrportal.hrportalapi.constant.employee.Series;
import az.hrportal.hrportalapi.domain.employee.Address;
import az.hrportal.hrportalapi.domain.employee.Business;
import az.hrportal.hrportalapi.domain.employee.ContactInfo;
import az.hrportal.hrportalapi.domain.employee.Education;
import az.hrportal.hrportalapi.domain.employee.ForeignPassport;
import az.hrportal.hrportalapi.domain.employee.IDCard;
import az.hrportal.hrportalapi.dto.employee.AddressRequestDto;
import az.hrportal.hrportalapi.dto.employee.BusinessRequestDto;
import az.hrportal.hrportalapi.dto.employee.ContactInfoRequestDto;
import az.hrportal.hrportalapi.dto.employee.EducationRequestDto;
import az.hrportal.hrportalapi.dto.employee.ForeignPassportRequestDto;
import az.hrportal.hrportalapi.dto.employee.IDCardRequestDto;
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
public interface EmployeeMapper {
    Address toAddress(AddressRequestDto addressRequestDto);

    ContactInfo toContactInfo(ContactInfoRequestDto contactInfoRequestDto);

    @Mapping(target = "jobEndDate", source = "jobEndDate", qualifiedByName = "stringToDate")
    @Mapping(target = "jobStartDate", source = "jobStartDate", qualifiedByName = "stringToDate")
    Business toBusiness(BusinessRequestDto businessRequestDto);

    @Mapping(target = "academicDegreeDate", source = "academicDegreeDate", qualifiedByName = "stringToDate")
    @Mapping(target = "entranceDate", source = "entranceDate", qualifiedByName = "stringToDate")
    @Mapping(target = "graduateDate", source = "graduateDate", qualifiedByName = "stringToDate")
    @Mapping(target = "graduateFileDate", source = "graduateFileDate", qualifiedByName = "stringToDate")
    @Mapping(target = "educationType", source = "educationType", qualifiedByName = "intToEducationType")
    Education toEducation(EducationRequestDto educationRequestDto);

    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "stringToDate")
    @Mapping(target = "endDate", source = "endDate", qualifiedByName = "stringToDate")
    @Mapping(target = "series", source = "series", qualifiedByName = "intToSeries")
    ForeignPassport toForeignPassport(ForeignPassportRequestDto foreignPassportRequestDto);

    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "stringToDate")
    @Mapping(target = "endDate", source = "endDate", qualifiedByName = "stringToDate")
    @Mapping(target = "series", source = "series", qualifiedByName = "intToSeries")
    IDCard toIdCard(IDCardRequestDto idCardRequestDto);

    @Named("stringToDate")
    @SneakyThrows
    default Date stringToDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.parse(date);
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
