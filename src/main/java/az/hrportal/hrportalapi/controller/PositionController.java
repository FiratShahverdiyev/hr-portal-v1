package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.dto.position.request.GeneralInfoRequestDto;
import az.hrportal.hrportalapi.dto.position.request.KnowledgeRequestDto;
import az.hrportal.hrportalapi.service.PositionService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("position")
@RequiredArgsConstructor
public class PositionController {
    private final PositionService positionService;

    @PostMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> create(@RequestBody @Valid GeneralInfoRequestDto generalInfoRequestDto) {
        return ResponseDto.of(positionService.saveGeneralInfo(generalInfoRequestDto), 200);
    }

    @PutMapping("knowledge/{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> update(@PathVariable Integer id,
                                       @RequestBody @Valid KnowledgeRequestDto knowledgeRequestDto) {
        return ResponseDto.of(positionService.updateKnowledge(id, knowledgeRequestDto), 200);
    }

    @PutMapping("general-info/{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> update(@PathVariable Integer id,
                                       @RequestBody @Valid GeneralInfoRequestDto generalInfoRequestDto) {
        return ResponseDto.of(positionService.updateGeneralInfo(id, generalInfoRequestDto), 200);
    }

    //TODO Delete on production
    @GetMapping("work-address")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<List<KeyValue<String, Integer>>> getAll() {
        return ResponseDto.of(positionService.getWorkAddress(), 200);
    }
}
