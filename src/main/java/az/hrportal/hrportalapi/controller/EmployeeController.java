package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.GeneralInfoRequestDto;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseDto<Integer> create(@RequestBody GeneralInfoRequestDto generalInfoRequestDto) {
        return ResponseDto.of(employeeService.saveGeneralInfo(generalInfoRequestDto));
    }
}
