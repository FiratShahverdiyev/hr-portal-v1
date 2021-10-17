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
    @Mapping(target = "eventFrom", source = "eventFrom", dateFormat = dateFormat)
    @Mapping(target = "eventTo", source = "eventTo", dateFormat = dateFormat)
    @Mapping(target = "callBackDate", source = "callBackDate", dateFormat = dateFormat)
    @Mapping(target = "assignmentTerm", source = "assignmentTerm", dateFormat = dateFormat)
    @Mapping(target = "workMode", source = "newWorkMode")
    @Mapping(target = "eventToBusinessTripDate", source = "eventToBusinessTripDate", dateFormat = dateFormat)
    @Mapping(target = "eventFromBusinessTripDate", source = "eventFromBusinessTripDate", dateFormat = dateFormat)
    @Mapping(target = "nonWorkDay", source = "nonWorkDay", dateFormat = dateFormat)
    @Mapping(target = "givenNonWorkDay", source = "givenNonWorkDay", dateFormat = dateFormat)
    @Mapping(target = "eventFrom2", source = "eventFrom2", dateFormat = dateFormat)
    @Mapping(target = "eventTo2", source = "eventTo2", dateFormat = dateFormat)
    Operation toOperation(DocumentData documentData);
}
