package az.hrportal.hrportalapi.mapper.employee;

import az.hrportal.hrportalapi.domain.embeddable.Certificate;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.employee.GovernmentAchievement;
import az.hrportal.hrportalapi.dto.employee.request.CertificateRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.GovernmentAchievementRequestDto;
import az.hrportal.hrportalapi.dto.employee.response.AcademicInfoResponseDto;
import lombok.SneakyThrows;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Date;
import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface AcademicInfoMapper {
    @Mapping(target = "academicDegreeDate", source = "education.academicDegreeDate", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "entranceDate", source = "education.entranceDate", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "graduateDate", source = "education.graduateDate", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "academicDegreeNumber", source = "education.academicDegreeNumber")
    @Mapping(target = "direction", source = "education.direction")
    @Mapping(target = "faculty", source = "education.faculty")
    @Mapping(target = "institution", source = "education.institution")
    @Mapping(target = "academicDegreeOrganization", source = "education.academicDegreeOrganization")
    @Mapping(target = "speciality", source = "education.speciality")
    @Mapping(target = "degree", source = "education.degree")
    @Mapping(target = "graduateFileNumber", source = "education.graduateFileNumber")
    @Mapping(target = "educationType", source = "education.educationType")
    @Mapping(target = "nostrifikasiyaNumber", source = "education.nostrifikasiyaNumber")
    @Mapping(target = "graduateFileDate", source = "education.graduateFileDate", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "driverCardCategory", source = "driverCardCategory")
    @Mapping(target = "driverCardEndDate", source = "driverCardEndDate", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    AcademicInfoResponseDto toAcademicInfoResponseDto(Employee employee);

    @IterableMapping(qualifiedByName = "toCertificateRequestDto")
    List<CertificateRequestDto> toCertificateRequestDtos(List<Certificate> certificates);

    @IterableMapping(qualifiedByName = "toGovernmentAchievementRequestDto")
    List<GovernmentAchievementRequestDto> toGovernmentAchievementRequestDtos(List<GovernmentAchievement>
                                                                                     governmentAchievements);

    @Named("toCertificateRequestDto")
    @Mapping(target = "endDate", source = "endDate", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    CertificateRequestDto toCertificateRequestDto(Certificate certificate);

    @Named("toGovernmentAchievementRequestDto")
    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "dateToString",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    GovernmentAchievementRequestDto toGovernmentAchievementRequestDto(GovernmentAchievement governmentAchievement);

    @Named("dateToString")
    @SneakyThrows
    default String dateToString(Date date) {
        return date.toString();
    }
}
