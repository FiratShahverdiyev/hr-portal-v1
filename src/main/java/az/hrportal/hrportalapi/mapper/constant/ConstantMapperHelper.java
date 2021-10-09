package az.hrportal.hrportalapi.mapper.constant;

import az.hrportal.hrportalapi.constant.DisciplineType;
import az.hrportal.hrportalapi.constant.Status;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface ConstantMapperHelper {
    @StatusToValueAz
    default String statusToValueAz(Status status) {
        return status.getValueAz();
    }

    @DisciplineTypeToValue
    default String disciplineTypeToValue(DisciplineType disciplineType) {
        return disciplineType.getValue();
    }
}
