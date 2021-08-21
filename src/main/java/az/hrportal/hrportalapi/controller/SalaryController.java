package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.service.SalaryService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("salary")
@RequiredArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;

    @GetMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<List<DropDownResponseDto>> getAll() {
        return ResponseDto.of(salaryService.getAll(), 200);
    }
}
