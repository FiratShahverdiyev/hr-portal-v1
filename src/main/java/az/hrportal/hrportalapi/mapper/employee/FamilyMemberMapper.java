package az.hrportal.hrportalapi.mapper.employee;

import az.hrportal.hrportalapi.domain.embeddable.FamilyMember;
import az.hrportal.hrportalapi.dto.employee.request.FamilyMemberRequestDto;
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
public interface FamilyMemberMapper {
    @IterableMapping(qualifiedByName = "toFamilyMember")
    List<FamilyMember> toFamilyMembers(List<FamilyMemberRequestDto> familyMemberRequestDtos);

    @Named("toFamilyMember")
    @Mapping(target = "birthday", source = "birthday", qualifiedByName = "stringToDate",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    FamilyMember toFamilyMember(FamilyMemberRequestDto familyMemberRequestDto);

    @Named("stringToDate")
    @SneakyThrows
    default Date stringToDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.parse(date);
    }
}
