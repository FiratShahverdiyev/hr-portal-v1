package az.hrportal.hrportalapi.service.position;

import az.hrportal.hrportalapi.domain.position.Salary;
import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import az.hrportal.hrportalapi.dto.position.request.SalaryRequestDto;
import az.hrportal.hrportalapi.mapper.DropDownMapper;
import az.hrportal.hrportalapi.repository.position.SalaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        salary.setSalary(salaryRequestDto.getSalary());
        salaryRepository.save(salary);
        log.info("********** create service completed with {} **********", salaryRequestDto);
        return true;
    }

    public List<DropDownResponseDto<BigDecimal>> getAll() {
        log.info("getAllSalaries service started");
        List<DropDownResponseDto<BigDecimal>> response = dropDownMapper
                .toSalaryResponseDtos(salaryRepository.findAll());
        log.info("********** getAllSalaries service completed **********");
        return response;
    }
}
