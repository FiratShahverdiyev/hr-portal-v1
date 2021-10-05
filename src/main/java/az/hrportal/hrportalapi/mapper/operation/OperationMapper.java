package az.hrportal.hrportalapi.mapper.operation;

import az.hrportal.hrportalapi.domain.operation.Operation;
import az.hrportal.hrportalapi.dto.document.DocumentData;
import az.hrportal.hrportalapi.mapper.operation.helper.IntToDocumentType;
import az.hrportal.hrportalapi.mapper.operation.helper.OperationMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import static az.hrportal.hrportalapi.constant.Constant.dateFormat;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = OperationMapperHelper.class)
public interface OperationMapper {

    @Mapping(target = "documentType", source = "documentType", qualifiedBy = IntToDocumentType.class)
    @Mapping(target = "joinDate", source = "joinDate", dateFormat = dateFormat)
    @Mapping(target = "changeDate", source = "changeDate", dateFormat = dateFormat)
    @Mapping(target = "dismissalDate", source = "dismissalDate", dateFormat = dateFormat)
    @Mapping(target = "workMode", source = "newWorkMode")
    Operation toOperation(DocumentData documentData);
}
