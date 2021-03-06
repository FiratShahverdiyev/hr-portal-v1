package az.hrportal.hrportalapi.service.employee;

import az.hrportal.hrportalapi.domain.employee.GovernmentAchievement;
import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import az.hrportal.hrportalapi.repository.employee.GovernmentAchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GovernmentAchievementService {
    private final GovernmentAchievementRepository governmentAchievementRepository;

    public List<DropDownResponseDto<String>> getAll() {
        log.info("getAll (GovernmentAchievement) service started");
        List<DropDownResponseDto<String>> response = new ArrayList<>();
        for (GovernmentAchievement governmentAchievement : governmentAchievementRepository.findAll()) {
            if (governmentAchievement.getName() == null)
                continue;
            DropDownResponseDto<String> responseDto = new DropDownResponseDto<>();
            responseDto.setName(governmentAchievement.getName());
            response.add(responseDto);
        }
        log.info("********** getAll (GovernmentAchievement) service completed **********");
        return response;
    }

    public List<DropDownResponseDto<String>> getOrganizations() {
        log.info("getOrganizations (GovernmentAchievement) service started");
        List<DropDownResponseDto<String>> response = new ArrayList<>();
        for (GovernmentAchievement governmentAchievement : governmentAchievementRepository.findAll()) {
            if (governmentAchievement.getOrganization() == null)
                continue;
            DropDownResponseDto<String> responseDto = new DropDownResponseDto<>();
            responseDto.setName(governmentAchievement.getOrganization());
            response.add(responseDto);
        }
        log.info("********** getOrganizations (GovernmentAchievement) service completed **********");
        return response;
    }
}
