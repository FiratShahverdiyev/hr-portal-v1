package az.hrportal.hrportalapi.mapper.operation.helper;

import az.hrportal.hrportalapi.constant.DocumentType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface OperationMapperHelper {

    @IntToDocumentType
    default DocumentType intToDocumentType(Integer documentType) {
        return DocumentType.intToEnum(documentType);
    }
}
