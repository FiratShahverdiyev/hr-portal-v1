package az.hrportal.hrportalapi.mapper.document;

import az.hrportal.hrportalapi.constant.DocumentType;
import az.hrportal.hrportalapi.domain.operation.Operation;
import az.hrportal.hrportalapi.dto.document.DocumentResponseDto;
import az.hrportal.hrportalapi.dto.document.GeneralDocumentInformation;
import az.hrportal.hrportalapi.mapper.constant.ConstantMapperHelper;
import az.hrportal.hrportalapi.mapper.constant.StatusToValueAz;
import az.hrportal.hrportalapi.mapper.position.helper.PositionMapperHelper;
import az.hrportal.hrportalapi.mapper.position.helper.WorkModeValue;
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
        componentModel = "spring", uses = {PositionMapperHelper.class, ConstantMapperHelper.class})
public interface DocumentResponseMapper {
    @Named("toDocumentResponseDto")
    @Mapping(target = "createDate", source = "createdAt", dateFormat = dateFormat)
    @Mapping(target = "documentType", source = "documentType", qualifiedByName = "documentToValueAz")
    @Mapping(target = "status", source = "status", qualifiedBy = StatusToValueAz.class)
    DocumentResponseDto toDocumentResponseDto(Operation operation);

    @IterableMapping(qualifiedByName = "toDocumentResponseDto")
    List<DocumentResponseDto> toDocumentResponseDtos(List<Operation> operations);

    @Mapping(target = "employeeId", source = "employee.id", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "positionId", source = "position.id", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "changeDate", source = "changeDate", dateFormat = dateFormat)
    @Mapping(target = "joinDate", source = "joinDate", dateFormat = dateFormat)
    @Mapping(target = "dismissalDate", source = "dismissalDate", dateFormat = dateFormat)
    @Mapping(target = "eventFrom", source = "eventFrom", dateFormat = dateFormat)
    @Mapping(target = "eventTo", source = "eventTo", dateFormat = dateFormat)
    @Mapping(target = "newWorkMode", source = "workMode",
            qualifiedBy = WorkModeValue.class, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "documentType", source = "documentType", qualifiedByName = "documentToValueAz")
    @Mapping(target = "documentId", source = "documentType", qualifiedByName = "documentToId")
    @Mapping(target = "status", source = "status", qualifiedBy = StatusToValueAz.class)
    GeneralDocumentInformation toGeneralDocumentInformation(Operation operation);

    @Named("documentToValueAz")
    default String documentToValueAz(DocumentType documentType) {
        return documentType.getValueAz();
    }

    @Named("documentToId")
    default Integer documentToId(DocumentType documentType) {
        return documentType.getValue();
    }
}
