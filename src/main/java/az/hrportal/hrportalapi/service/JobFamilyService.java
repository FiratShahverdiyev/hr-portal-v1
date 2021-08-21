package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import az.hrportal.hrportalapi.mapper.DropDownMapper;
import az.hrportal.hrportalapi.repository.position.JobFamilyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobFamilyService {
    private final JobFamilyRepository jobFamilyRepository;
    private final DropDownMapper dropDownMapper;

    public List<DropDownResponseDto> getAll() {
        log.info("getAllJobFamilies service started");
        List<DropDownResponseDto> response = dropDownMapper.toJobFamilyResponseDtos(jobFamilyRepository.findAll());
        log.info("********** getAllJobFamilies service completed **********");
        return response;
    }
}
