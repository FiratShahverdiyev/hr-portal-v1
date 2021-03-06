package az.hrportal.hrportalapi.service.position;

import az.hrportal.hrportalapi.domain.position.Vacancy;
import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import az.hrportal.hrportalapi.dto.position.request.VacancyRequestDto;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
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

    public Boolean create(VacancyRequestDto vacancyRequestDto) {
        log.info("create service started with {}", vacancyRequestDto);
        Vacancy vacancy = new Vacancy();
        vacancy.setName(vacancyRequestDto.getName());
        vacancyRepository.save(vacancy);
        log.info("********** create service completed with {} **********", vacancyRequestDto);
        return true;
    }

    public List<DropDownResponseDto<String>> getAll() {
        log.info("getAllVacancies service started");
        List<DropDownResponseDto<String>> response = dropDownMapper.toVacancyResponseDtos(vacancyRepository.findAll());
        log.info("********** getAllVacancies service completed **********");
        return response;
    }

    public String delete(String name) {
        log.info("delete service started with name, {}", name);
        Vacancy vacancy = vacancyRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(Vacancy.class, name));
        vacancyRepository.delete(vacancy);
        log.info("********** delete service completed with id, {} ********** ", name);
        return name;
    }
}
