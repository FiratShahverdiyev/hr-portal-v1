package az.hrportal.hrportalapi.service.position;

import az.hrportal.hrportalapi.domain.position.JobFamily;
import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import az.hrportal.hrportalapi.dto.position.request.JobFamilyRequestDto;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
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

    public Boolean create(JobFamilyRequestDto jobFamilyRequestDto) {
        log.info("create service started with {}", jobFamilyRequestDto);
        JobFamily jobFamily = new JobFamily();
        jobFamily.setName(jobFamilyRequestDto.getName());
        jobFamilyRepository.save(jobFamily);
        log.info("********** create service completed with {} **********", jobFamilyRequestDto);
        return true;
    }

    public List<DropDownResponseDto<String>> getAll() {
        log.info("getAllJobFamilies service started");
        List<DropDownResponseDto<String>> response = dropDownMapper
                .toJobFamilyResponseDtos(jobFamilyRepository.findAll());
        log.info("********** getAllJobFamilies service completed **********");
        return response;
    }

    public String delete(String name) {
        log.info("delete service started with name, {}", name);
        JobFamily jobFamily = jobFamilyRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(JobFamily.class, name));
        jobFamilyRepository.delete(jobFamily);
        log.info("********** delete service completed with id, {} **********", name);
        return name;
    }
}
