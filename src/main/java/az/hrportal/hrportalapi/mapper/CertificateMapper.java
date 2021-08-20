package az.hrportal.hrportalapi.mapper;

import az.hrportal.hrportalapi.domain.embeddable.Certificate;
import az.hrportal.hrportalapi.dto.employee.request.CertificateRequestDto;
import lombok.SneakyThrows;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface CertificateMapper {
    @IterableMapping(qualifiedByName = "toCertificate")
    List<Certificate> toCertificates(List<CertificateRequestDto> certificateRequestDtos);

    @Named("toCertificate")
    @Mapping(target = "endDate", source = "endDate", qualifiedByName = "stringToDate",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Certificate toCertificate(CertificateRequestDto certificateRequestDto);

    @Named("stringToDate")
    @SneakyThrows
    default Date stringToDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.parse(date);
    }
}
