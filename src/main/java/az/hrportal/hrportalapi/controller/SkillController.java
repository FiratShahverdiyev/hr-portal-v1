package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.service.position.SkillService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("skill")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @PostMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> create(String skill) {
        return ResponseDto.of(skillService.save(skill), 200);
    }

    @GetMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Set<KeyValue<String, String>>> getAll() {
        return ResponseDto.of(skillService.getAll(), 200);
    }

    @DeleteMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> delete(Integer id) {
        return ResponseDto.of(skillService.delete(id), 200);
    }
}
