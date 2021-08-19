package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.AcademicRequestDto;
import az.hrportal.hrportalapi.dto.BusinessRequestDto;
import az.hrportal.hrportalapi.dto.GeneralInfoRequestDto;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("business/{id}")
    public ResponseDto<Integer> update(@PathVariable Integer id, @RequestBody BusinessRequestDto businessRequestDto) {
        return ResponseDto.of(employeeService.updateBusiness(id, businessRequestDto));
    }

    @PutMapping("academic/{id}")
    public ResponseDto<Integer> update(@PathVariable Integer id, @RequestBody AcademicRequestDto academicRequestDto) {
        return ResponseDto.of(employeeService.updateAcademic(id, academicRequestDto));
    }
}
