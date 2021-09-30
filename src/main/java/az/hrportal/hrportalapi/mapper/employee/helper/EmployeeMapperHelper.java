package az.hrportal.hrportalapi.mapper.employee.helper;

import az.hrportal.hrportalapi.constant.employee.BloodGroup;
import az.hrportal.hrportalapi.constant.employee.EducationType;
import az.hrportal.hrportalapi.constant.employee.FamilyCondition;
import az.hrportal.hrportalapi.constant.employee.Gender;
import az.hrportal.hrportalapi.constant.employee.MilitaryAchievement;
import az.hrportal.hrportalapi.constant.employee.RelationType;
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

import static az.hrportal.hrportalapi.constant.Constant.dateFormat;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface EmployeeMapperHelper {
    @DtoToGovernmentAchievement
    @IterableMapping(qualifiedByName = "toGovernmentAchievement")
    Set<GovernmentAchievement> toGovernmentAchievements(List<GovernmentAchievementRequestDto>
                                                                governmentAchievementRequestDtos);

    @Named("toGovernmentAchievement")
    @Mapping(target = "startDate", source = "startDate", dateFormat = dateFormat)
    GovernmentAchievement toGovernmentAchievement(GovernmentAchievementRequestDto governmentAchievementRequestDto);

    @DtoToCertificates
    @IterableMapping(qualifiedByName = "toCertificate")
    Set<Certificate> toCertificates(List<CertificateRequestDto> certificateRequestDtos);

    @Named("toCertificate")
    @Mapping(target = "endDate", source = "endDate", dateFormat = dateFormat)
    Certificate toCertificate(CertificateRequestDto certificateRequestDto);

    @DtoToFamilyMembers
    @IterableMapping(qualifiedByName = "toFamilyMember")
    Set<FamilyMember> toFamilyMembers(List<FamilyMemberRequestDto> familyMemberRequestDtos);

    @Named("toFamilyMember")
    @Mapping(target = "birthday", source = "birthday", dateFormat = dateFormat)
    FamilyMember toFamilyMember(FamilyMemberRequestDto familyMemberRequestDto);

    @FamilyMembersToDto
    @IterableMapping(qualifiedByName = "toFamilyMemberResponseDto")
    List<FamilyMemberResponseDto> toFamilyMemberResponseDtos(Set<FamilyMember> familyMembers);

    @Named("toFamilyMemberResponseDto")
    @Mapping(target = "birthday", source = "birthday", dateFormat = dateFormat)
    @Mapping(target = "relationType", source = "relationType", qualifiedBy = RelationTypeValue.class)
    FamilyMemberResponseDto toFamilyMemberResponseDto(FamilyMember familyMember);

    @GovernmentAchievementToDto
    @IterableMapping(qualifiedByName = "toGovernmentAchievementDto")
    List<GovernmentAchievementResponseDto> toGovernmentAchievementRequestDtos(Set<GovernmentAchievement>
                                                                                      governmentAchievements);

    @Named("toGovernmentAchievementDto")
    @Mapping(target = "startDate", source = "startDate", dateFormat = dateFormat)
    GovernmentAchievementResponseDto toGovernmentAchievementDto(GovernmentAchievement governmentAchievement);

    @CertificatesToDto
    @IterableMapping(qualifiedByName = "toCertificateDto")
    List<CertificateResponseDto> toCertificateDtos(Set<Certificate> certificates);

    @Named("toCertificateDto")
    @Mapping(target = "endDate", source = "endDate", dateFormat = dateFormat)
    CertificateResponseDto toCertificateDto(Certificate certificate);

    @BloodGroupValue
    default String getBloodGroupValue(BloodGroup bloodGroup) {
        return bloodGroup.getValue();
    }

    @EducationTypeValue
    default String getEducationTypeValue(EducationType educationType) {
        return educationType.getValue();
    }

    @FamilyConditionValue
    default String getFamilyConditionValue(FamilyCondition familyCondition) {
        return familyCondition.getValue();
    }

    @GenderValue
    default String getGenderValue(Gender gender) {
        return gender.getValue();
    }

    @MilitaryAchievementValue
    default String getMilitaryAchievementValue(MilitaryAchievement militaryAchievement) {
        return militaryAchievement.getValue();
    }

    @RelationTypeValue
    default String getRelationTypeValue(RelationType relationType) {
        return relationType.getValue();
    }
}
