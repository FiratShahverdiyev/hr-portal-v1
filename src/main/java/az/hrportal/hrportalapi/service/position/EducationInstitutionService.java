package az.hrportal.hrportalapi.service.position;

import az.hrportal.hrportalapi.domain.position.EducationInstitution;
import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import az.hrportal.hrportalapi.dto.position.request.InstitutionRequestDto;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.mapper.DropDownMapper;
import az.hrportal.hrportalapi.repository.EducationInstitutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class EducationInstitutionService {
    private final EducationInstitutionRepository educationInstitutionRepository;
    private final DropDownMapper dropDownMapper;

    public Boolean create(InstitutionRequestDto institutionRequestDto) {
        log.info("create service started with {}", institutionRequestDto);
        EducationInstitution educationInstitution = new EducationInstitution();
        educationInstitution.setName(institutionRequestDto.getName());
        educationInstitutionRepository.save(educationInstitution);
        log.info("********** create service completed with {} **********", institutionRequestDto);
        return true;
    }

    public List<DropDownResponseDto<String>> getAll() {
        log.info("getAllInstitutions service started");
        List<DropDownResponseDto<String>> response = dropDownMapper
                .toEducationInstitutionResponseDtos(educationInstitutionRepository.findAll());
        log.info("********** getAllInstitutions service completed **********");
        return response;
    }

    public String delete(String name) {
        log.info("delete service started with name, {}", name);
        EducationInstitution educationInstitution = educationInstitutionRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(EducationInstitution.class, name));
        educationInstitutionRepository.delete(educationInstitution);
        log.info("********** delete service completed with name, {} **********", name);
        return name;
    }
}
