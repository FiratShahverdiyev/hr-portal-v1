package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.PaginationResponseDto;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.EmployeeSalaryResponseDto;
import az.hrportal.hrportalapi.service.EmployeeSalaryService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("employee-salary")
@RequiredArgsConstructor
public class EmployeeSalaryController {
    private final EmployeeSalaryService employeeSalaryService;

    @GetMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<PaginationResponseDto<List<EmployeeSalaryResponseDto>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseDto.of(employeeSalaryService.getAll(page, size), 200);
    }

    @GetMapping("calculate")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<PaginationResponseDto<List<EmployeeSalaryResponseDto>>> calculateAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam int year, @RequestParam int month) {
        return ResponseDto.of(employeeSalaryService.calculateAndGetAll(page, size, year, month), 200);
    }
}
