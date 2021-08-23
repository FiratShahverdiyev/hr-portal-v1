package az.hrportal.hrportalapi.service.position;

import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import az.hrportal.hrportalapi.mapper.DropDownMapper;
import az.hrportal.hrportalapi.repository.position.VacancyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VacancyService {
    private final VacancyRepository vacancyRepository;
    private final DropDownMapper dropDownMapper;

    public List<DropDownResponseDto<String>> getAll() {
        log.info("getAllVacancies service started");
        List<DropDownResponseDto<String>> response = dropDownMapper.toVacancyResponseDtos(vacancyRepository.findAll());
        log.info("********** getAllVacancies service completed **********");
        return response;
    }
}
