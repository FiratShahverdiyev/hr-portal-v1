package az.hrportal.hrportalapi.mapper.employee;

import az.hrportal.hrportalapi.domain.employee.GovernmentAchievement;
import az.hrportal.hrportalapi.dto.employee.request.GovernmentAchievementRequestDto;
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
public interface GovernmentAchievementMapper {
    @IterableMapping(qualifiedByName = "toGovernmentAchievement")
    List<GovernmentAchievement> toGovernmentAchievements(List<GovernmentAchievementRequestDto>
                                                                governmentAchievementRequestDtos);

    @Named("toGovernmentAchievement")
    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "stringToDate",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    GovernmentAchievement toGovernmentAchievement(GovernmentAchievementRequestDto governmentAchievementRequestDto);

    @Named("stringToDate")
    @SneakyThrows
    default Date stringToDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.parse(date);
    }
}
