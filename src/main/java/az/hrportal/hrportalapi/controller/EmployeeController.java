package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.dto.employee.request.AcademicRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.BusinessRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.EmployeeGeneralInfoRequestDto;
import az.hrportal.hrportalapi.dto.employee.response.AcademicInfoResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.BusinessResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.EmployeeResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.GeneralInfoResponseDto;
import az.hrportal.hrportalapi.service.employee.EmployeeService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> create(@RequestBody EmployeeGeneralInfoRequestDto employeeGeneralInfoRequestDto) {
        return ResponseDto.of(employeeService.saveGeneralInfo(employeeGeneralInfoRequestDto), 200);
    }

    @PutMapping("general-info/{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> update(@PathVariable Integer id, @RequestBody @Valid EmployeeGeneralInfoRequestDto
            employeeGeneralInfoRequestDto) {
        return ResponseDto.of(employeeService.updateGeneralInfo(id, employeeGeneralInfoRequestDto), 200);
    }

    @PutMapping("business-info/{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> update(@PathVariable Integer id, @RequestBody @Valid
            BusinessRequestDto businessRequestDto) {
        return ResponseDto.of(employeeService.updateBusiness(id, businessRequestDto), 200);
    }

    @PutMapping("academic-info/{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> update(@PathVariable Integer id, @RequestBody @Valid
            AcademicRequestDto academicRequestDto) {
        return ResponseDto.of(employeeService.updateAcademic(id, academicRequestDto), 200);
    }

    @GetMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<List<EmployeeResponseDto>> getAll() {
        return ResponseDto.of(employeeService.getAll(), 200);
    }

    @GetMapping("general-info/{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<GeneralInfoResponseDto> getGeneralInfo(@PathVariable Integer id) {
        return ResponseDto.of(employeeService.getGeneralInfoById(id), 200);
    }

    @GetMapping("academic-info/{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<AcademicInfoResponseDto> getAcademicInfo(@PathVariable Integer id) {
        return ResponseDto.of(employeeService.getAcademicInfoById(id), 200);
    }

    @GetMapping("business-info/{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<BusinessResponseDto> getBusinessInfo(@PathVariable Integer id) {
        return ResponseDto.of(employeeService.getBusinessInfoById(id));
    }

    @GetMapping("fullname-position")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<List<String>> getAllFullNameAndPosition() {
        return ResponseDto.of(employeeService.getAllFullNameAndPosition(), 200);
    }

    //TODO Delete on production
    @GetMapping("quota")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<List<KeyValue<String, Integer>>> getAllQuota() {
        return ResponseDto.of(employeeService.getAllQuotas(), 200);
    }

    @DeleteMapping("{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> delete(@PathVariable Integer id) {
        return ResponseDto.of(employeeService.delete(id), 200);
    }
}
