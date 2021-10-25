package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.dto.position.request.SalaryRequestDto;
import az.hrportal.hrportalapi.service.position.SalaryService;
import com.amazonaws.Response;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("salary")
@RequiredArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;

    @PostMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Boolean> create(@RequestBody SalaryRequestDto salaryRequestDto) {
        return ResponseDto.of(salaryService.create(salaryRequestDto), 200);
    }

    @GetMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<List<DropDownResponseDto<Float>>> getAll() {
        return ResponseDto.of(salaryService.getAll(), 200);
    }

    @DeleteMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> delete(Integer id) {
        return ResponseDto.of(salaryService.delete(id), 200);
    }

}
