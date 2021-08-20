package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.dto.employee.AcademicRequestDto;
import az.hrportal.hrportalapi.dto.employee.BusinessRequestDto;
import az.hrportal.hrportalapi.dto.employee.GeneralInfoRequestDto;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.service.EmployeeService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> create(@RequestBody GeneralInfoRequestDto generalInfoRequestDto) {
        return ResponseDto.of(employeeService.saveGeneralInfo(generalInfoRequestDto), 200);
    }

    @PutMapping("business/{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> update(@PathVariable Integer id, @RequestBody BusinessRequestDto businessRequestDto) {
        return ResponseDto.of(employeeService.updateBusiness(id, businessRequestDto), 200);
    }

    @PutMapping("academic/{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> update(@PathVariable Integer id, @RequestBody AcademicRequestDto academicRequestDto) {
        return ResponseDto.of(employeeService.updateAcademic(id, academicRequestDto), 200);
    }

    @DeleteMapping("{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> delete(@PathVariable Integer id) {
        return ResponseDto.of(employeeService.delete(id), 200);
    }

    @PutMapping("{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Employee> update(@PathVariable Integer id, @RequestBody Employee employee) {
        return ResponseDto.of(employeeService.update(id, employee), 200);
    }
}
