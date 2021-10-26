package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.dto.position.request.DepartmentRequestDto;
import az.hrportal.hrportalapi.service.position.DepartmentService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Boolean> create(@RequestBody DepartmentRequestDto departmentRequestDto) {
        return ResponseDto.of(departmentService.create(departmentRequestDto), 200);
    }

    @GetMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<List<DropDownResponseDto<String>>> getAll() {
        return ResponseDto.of(departmentService.getAll(), 200);
    }

    @GetMapping("/sub-department/{department}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<List<DropDownResponseDto<String>>> getAllSubDepartments(@PathVariable String department) {
        return ResponseDto.of(departmentService.getAllByDepartment(department), 200);
    }

    @DeleteMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<String> deleteDepartment(String name) {
        return ResponseDto.of(departmentService.delete(name), 200);
    }
}
