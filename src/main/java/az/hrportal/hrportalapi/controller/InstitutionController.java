package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.dto.position.request.InstitutionRequestDto;
import az.hrportal.hrportalapi.service.position.EducationInstitutionService;
import az.hrportal.hrportalapi.service.position.InstitutionService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("institution")
@RequiredArgsConstructor
public class InstitutionController {
    private final InstitutionService institutionService;
    private final EducationInstitutionService educationInstitutionService;

    @PostMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Boolean> create(@RequestBody InstitutionRequestDto institutionRequestDto) {
        return ResponseDto.of(institutionService.create(institutionRequestDto), 200);
    }

    @GetMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<List<DropDownResponseDto<String>>> getAll() {
        return ResponseDto.of(institutionService.getAll(), 200);
    }

    @DeleteMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<String> delete(String name) {
        return ResponseDto.of(institutionService.delete(name), 200);
    }

    @PostMapping("/education")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Boolean> createEducationInstitution(@RequestBody InstitutionRequestDto institutionRequestDto) {
        return ResponseDto.of(educationInstitutionService.create(institutionRequestDto), 200);
    }

    @GetMapping("/education")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<List<DropDownResponseDto<String>>> getAllEducationInstitution() {
        return ResponseDto.of(educationInstitutionService.getAll(), 200);
    }

    @DeleteMapping("/education")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<String> deleteEducationInstitution(String name) {
        return ResponseDto.of(educationInstitutionService.delete(name), 200);
    }

}
