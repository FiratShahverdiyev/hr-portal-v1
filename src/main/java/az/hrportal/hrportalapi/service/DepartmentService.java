package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.domain.position.Department;
import az.hrportal.hrportalapi.dto.DropDownResponseDto;
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

    public List<DropDownResponseDto> getAllDepartments() {
        log.info("getAllDepartments service started");
        List<DropDownResponseDto> response = dropDownMapper.toDepartmentResponseDtos(departmentRepository.findAll());
        log.info("********** getAllDepartments service completed **********");
        return response;
    }

    public List<DropDownResponseDto> getAllSubDepartmentsByDepartment(String departmentName) {
        log.info("getAllSubDepartmentsByDepartment service started with department : {}", departmentName);
        List<DropDownResponseDto> subDepartments = dropDownMapper
                .toSubDepartmentResponseDtos(departmentRepository.findByName(departmentName).orElseThrow(() ->
                        new EntityNotFoundException(Department.class, departmentName)).getSubDepartment());
        log.info("********** getAllSubDepartmentsByDepartment service completed **********");
        return subDepartments;
    }
}