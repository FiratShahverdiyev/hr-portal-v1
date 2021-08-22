package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.dto.DropDownResponseDto;
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

    public List<DropDownResponseDto<String>> getAll() {
        log.info("getAllInstitutions service started");
        List<DropDownResponseDto<String>> response = dropDownMapper.toInstitutionResponseDtos(institutionRepository.findAll());
        log.info("********** getAllInstitutions service completed **********");
        return response;
    }
}
