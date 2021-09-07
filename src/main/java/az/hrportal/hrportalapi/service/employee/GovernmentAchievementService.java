package az.hrportal.hrportalapi.service.employee;

import az.hrportal.hrportalapi.domain.employee.GovernmentAchievement;
import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import az.hrportal.hrportalapi.mapper.DropDownMapper;
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
    private final DropDownMapper dropDownMapper;

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
        return response;
    }

   /* public Integer create(String name) {
        log.info("create (GovernmentAchievement) service started with name : {}", name);
        GovernmentAchievement governmentAchievement = new GovernmentAchievement();
        governmentAchievement.setName(name);
        GovernmentAchievement saved = governmentAchievementRepository.save(governmentAchievement);
        log.info("********** create (GovernmentAchievement) service completed with name : {} **********", name);
        return saved.getId();
    }*/
}
