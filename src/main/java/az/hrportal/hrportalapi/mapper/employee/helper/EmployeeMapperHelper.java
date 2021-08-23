package az.hrportal.hrportalapi.mapper.employee.helper;

import az.hrportal.hrportalapi.domain.embeddable.Certificate;
import az.hrportal.hrportalapi.domain.embeddable.FamilyMember;
import az.hrportal.hrportalapi.domain.employee.GovernmentAchievement;
import az.hrportal.hrportalapi.dto.employee.request.CertificateRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.FamilyMemberRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.GovernmentAchievementRequestDto;
import az.hrportal.hrportalapi.dto.employee.response.CertificateResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.FamilyMemberResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.GovernmentAchievementResponseDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface EmployeeMapperHelper {
    @DtoToGovernmentAchievement
    @IterableMapping(qualifiedByName = "toGovernmentAchievement")
    Set<GovernmentAchievement> toGovernmentAchievements(List<GovernmentAchievementRequestDto>
                                                                governmentAchievementRequestDtos);

    @Named("toGovernmentAchievement")
    @Mapping(target = "startDate", source = "startDate", dateFormat = "dd-MM-yyyy")
    GovernmentAchievement toGovernmentAchievement(GovernmentAchievementRequestDto governmentAchievementRequestDto);

    @DtoToCertificates
    @IterableMapping(qualifiedByName = "toCertificate")
    Set<Certificate> toCertificates(List<CertificateRequestDto> certificateRequestDtos);

    @Named("toCertificate")
    @Mapping(target = "endDate", source = "endDate", dateFormat = "dd-MM-yyyy")
    Certificate toCertificate(CertificateRequestDto certificateRequestDto);

    @DtoToFamilyMembers
    @IterableMapping(qualifiedByName = "toFamilyMember")
    Set<FamilyMember> toFamilyMembers(List<FamilyMemberRequestDto> familyMemberRequestDtos);

    @Named("toFamilyMember")
    @Mapping(target = "birthday", source = "birthday", dateFormat = "dd-MM-yyyy")
    FamilyMember toFamilyMember(FamilyMemberRequestDto familyMemberRequestDto);

    @FamilyMembersToDto
    @IterableMapping(qualifiedByName = "toFamilyMemberResponseDto")
    List<FamilyMemberResponseDto> toFamilyMemberResponseDtos(Set<FamilyMember> familyMembers);

    @Named("toFamilyMemberResponseDto")
    @Mapping(target = "birthday", source = "birthday", dateFormat = "dd-MM-yyyy")
    FamilyMemberResponseDto toFamilyMemberResponseDto(FamilyMember familyMember);

    @GovernmentAchievementToDto
    @IterableMapping(qualifiedByName = "toGovernmentAchievementDto")
    List<GovernmentAchievementResponseDto> toGovernmentAchievementRequestDtos(Set<GovernmentAchievement>
                                                                                      governmentAchievements);

    @Named("toGovernmentAchievementDto")
    @Mapping(target = "startDate", source = "startDate", dateFormat = "dd-MM-yyyy")
    GovernmentAchievementResponseDto toGovernmentAchievementDto(GovernmentAchievement governmentAchievement);

    @CertificatesToDto
    @IterableMapping(qualifiedByName = "toCertificateDto")
    List<CertificateResponseDto> toCertificateDtos(Set<Certificate> certificates);

    @Named("toCertificateDto")
    @Mapping(target = "endDate", source = "endDate", dateFormat = "dd-MM-yyyy")
    CertificateResponseDto toCertificateDto(Certificate certificate);
}
