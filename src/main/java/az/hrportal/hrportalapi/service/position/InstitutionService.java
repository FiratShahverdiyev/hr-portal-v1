package az.hrportal.hrportalapi.service.position;

import az.hrportal.hrportalapi.domain.position.Institution;
import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import az.hrportal.hrportalapi.dto.position.request.InstitutionRequestDto;
import az.hrportal.hrportalapi.mapper.DropDownMapper;
import az.hrportal.hrportalapi.repository.position.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class InstitutionService {
    private final InstitutionRepository institutionRepository;
    private final DropDownMapper dropDownMapper;

    public Boolean create(InstitutionRequestDto institutionRequestDto) {
        log.info("create service started with {}", institutionRequestDto);
        Institution institution = new Institution();
        institution.setName(institutionRequestDto.getName());
        institutionRepository.save(institution);
        log.info("********** create service completed with {} **********", institutionRequestDto);
        return true;
    }

    public List<DropDownResponseDto<String>> getAll() {
        log.info("getAllInstitutions service started");
        List<DropDownResponseDto<String>> response = dropDownMapper
                .toInstitutionResponseDtos(institutionRepository.findAll());
        log.info("********** getAllInstitutions service completed **********");
        return response;
    }
}
