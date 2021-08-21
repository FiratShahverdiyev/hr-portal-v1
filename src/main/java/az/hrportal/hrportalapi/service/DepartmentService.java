package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import az.hrportal.hrportalapi.mapper.DropDownMapper;
import az.hrportal.hrportalapi.repository.position.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DropDownMapper dropDownMapper;

    public List<DropDownResponseDto> getAllDepartments() {
        log.info("getAllCountries service started");
        List<DropDownResponseDto> response = dropDownMapper.toDepartmentResponseDtos(departmentRepository.findAll());
        log.info("********** getAllCountries service completed **********");
        return response;
    }
}
