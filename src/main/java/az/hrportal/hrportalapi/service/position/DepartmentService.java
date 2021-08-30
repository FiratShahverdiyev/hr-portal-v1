package az.hrportal.hrportalapi.service.position;

import az.hrportal.hrportalapi.domain.position.Department;
import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import az.hrportal.hrportalapi.dto.position.request.DepartmentRequestDto;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
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

    public Boolean create(DepartmentRequestDto departmentRequestDto) {
        log.info("create service started with {}", departmentRequestDto);
        Department department = new Department();
        department.setName(departmentRequestDto.getName());
        departmentRepository.save(department);
        log.info("create service completed with {}", departmentRequestDto);
        return true;
    }

    public List<DropDownResponseDto<String>> getAll() {
        log.info("getAllDepartments service started");
        List<DropDownResponseDto<String>> response = dropDownMapper
                .toDepartmentResponseDtos(departmentRepository.findAll());
        log.info("********** getAllDepartments service completed **********");
        return response;
    }

    public List<DropDownResponseDto<String>> getAllByDepartment(String departmentName) {
        log.info("getAllSubDepartmentsByDepartment service started with department : {}", departmentName);
        List<DropDownResponseDto<String>> subDepartments = dropDownMapper
                .toSubDepartmentResponseDtos(departmentRepository.findByName(departmentName).orElseThrow(() ->
                        new EntityNotFoundException(Department.class, departmentName)).getSubDepartment());
        log.info("********** getAllSubDepartmentsByDepartment service completed **********");
        return subDepartments;
    }
}
