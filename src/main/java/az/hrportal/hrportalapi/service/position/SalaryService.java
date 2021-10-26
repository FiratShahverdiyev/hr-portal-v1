package az.hrportal.hrportalapi.service.position;

import az.hrportal.hrportalapi.domain.position.Salary;
import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import az.hrportal.hrportalapi.dto.position.request.SalaryRequestDto;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.mapper.DropDownMapper;
import az.hrportal.hrportalapi.repository.position.SalaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SalaryService {
    private final SalaryRepository salaryRepository;
    private final DropDownMapper dropDownMapper;

    public Boolean create(SalaryRequestDto salaryRequestDto) {
        log.info("create service started with {}", salaryRequestDto);
        Salary salary = new Salary();
        salary.setAmount(salaryRequestDto.getSalary());
        salaryRepository.save(salary);
        log.info("********** create service completed with {} **********", salaryRequestDto);
        return true;
    }

    public List<DropDownResponseDto<Float>> getAll() {
        log.info("getAllSalaries service started");
        List<DropDownResponseDto<Float>> response = dropDownMapper
                .toSalaryResponseDtos(salaryRepository.findAll());
        log.info("********** getAllSalaries service completed **********");
        return response;
    }

    public Integer delete(Integer id) {
        log.info("delete service started with id, {}", id);
        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Salary.class, id));
        salaryRepository.delete(salary);
        log.info("********** delete service completed with id, {} ********** ", id);
        return id;
    }
}
