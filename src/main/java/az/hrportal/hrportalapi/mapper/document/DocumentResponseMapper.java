package az.hrportal.hrportalapi.mapper.document;

import az.hrportal.hrportalapi.domain.operation.Operation;
import az.hrportal.hrportalapi.dto.document.DocumentResponseDto;
import az.hrportal.hrportalapi.mapper.position.helper.PositionMapperHelper;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static az.hrportal.hrportalapi.constant.Constant.dateFormat;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = PositionMapperHelper.class)
public interface DocumentResponseMapper {
    @Named("toDocumentResponseDto")
    @Mapping(target = "createDate", source = "createdAt", dateFormat = dateFormat)
    DocumentResponseDto toDocumentResponseDto(Operation operation);

    @IterableMapping(qualifiedByName = "toDocumentResponseDto")
    List<DocumentResponseDto> toDocumentResponseDtos(List<Operation> operations);
}
