package az.hrportal.hrportalapi.mapper.document;

import az.hrportal.hrportalapi.constant.DocumentType;
import az.hrportal.hrportalapi.domain.operation.Operation;
import az.hrportal.hrportalapi.dto.document.DocumentResponseDto;
import az.hrportal.hrportalapi.dto.document.GeneralDocumentInformation;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static az.hrportal.hrportalapi.constant.Constant.dateFormat;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface DocumentResponseMapper {
    @Named("toDocumentResponseDto")
    @Mapping(target = "createDate", source = "createdAt", dateFormat = dateFormat)
    DocumentResponseDto toDocumentResponseDto(Operation operation);

    @IterableMapping(qualifiedByName = "toDocumentResponseDto")
    @Mapping(target = "documentType", source = "documentType", qualifiedByName = "toValueAz")
    List<DocumentResponseDto> toDocumentResponseDtos(List<Operation> operations);

    @Mapping(target = "employeeId", source = "employee.id", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "positionId", source = "position.id", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "changeDate", source = "changeDate", dateFormat = dateFormat)
    @Mapping(target = "joinDate", source = "joinDate", dateFormat = dateFormat)
    @Mapping(target = "dismissalDate", source = "dismissalDate", dateFormat = dateFormat)
    GeneralDocumentInformation toGeneralDocumentInformation(Operation operation);

    @Named("toValueAz")
    default String toValueAz(DocumentType documentType) {
        return documentType.getValueAz();
    }
}
