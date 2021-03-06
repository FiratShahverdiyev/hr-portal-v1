package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.dto.PaginationResponseDto;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.dto.document.DocumentResponseDto;
import az.hrportal.hrportalapi.dto.position.request.GeneralInfoRequestDto;
import az.hrportal.hrportalapi.dto.position.request.KnowledgeRequestDto;
import az.hrportal.hrportalapi.dto.position.request.PositionFilterRequestDto;
import az.hrportal.hrportalapi.dto.position.response.GeneralInfoResponseDto;
import az.hrportal.hrportalapi.dto.position.response.KnowledgeResponseDto;
import az.hrportal.hrportalapi.dto.position.response.PositionResponseDto;
import az.hrportal.hrportalapi.service.position.PositionService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("position")
@RequiredArgsConstructor
public class PositionController {
    private final PositionService positionService;

    @PostMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> create(@RequestBody GeneralInfoRequestDto generalInfoRequestDto) {
        return ResponseDto.of(positionService.saveGeneralInfo(generalInfoRequestDto), 200);
    }

    @PutMapping("knowledge/{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> update(@PathVariable Integer id,
                                       @RequestBody KnowledgeRequestDto knowledgeRequestDto) {
        return ResponseDto.of(positionService.updateKnowledge(id, knowledgeRequestDto), 200);
    }

    @PutMapping("general-info/{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> update(@PathVariable Integer id,
                                       @RequestBody GeneralInfoRequestDto generalInfoRequestDto) {
        return ResponseDto.of(positionService.updateGeneralInfo(id, generalInfoRequestDto), 200);
    }

    @PutMapping("status/{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> changeStatus(@PathVariable Integer id, @RequestParam Integer status) {
        return ResponseDto.of(positionService.changeStatus(id, status));
    }

    /*@DeleteMapping("{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> delete(@PathVariable Integer id) {
        return ResponseDto.of(positionService.delete(id), 200);
    }*/

    @GetMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<PaginationResponseDto<List<PositionResponseDto>>> getAll(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String department, @RequestParam(required = false) String subDepartment,
            @RequestParam(required = false) String vacancy, @RequestParam(required = false) Integer vacancyCount) {
        return ResponseDto.of(positionService.getPaginationWithSearch(page, size,
                new PositionFilterRequestDto(department, subDepartment, vacancy, vacancyCount)), 200);
    }

    @GetMapping("document/{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<PaginationResponseDto<List<DocumentResponseDto>>> getDocumentsById(
            @PathVariable Integer id, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseDto.of(positionService.getDocumentsById(id, page, size), 200);
    }

    @GetMapping("general-info/{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<GeneralInfoResponseDto> getGeneralInfo(@PathVariable Integer id) {
        return ResponseDto.of(positionService.getGeneralInfoById(id), 200);
    }

    @GetMapping("knowledge/{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<KnowledgeResponseDto> getKnowledge(@PathVariable Integer id) {
        return ResponseDto.of(positionService.getKnowledgeById(id), 200);
    }

    @GetMapping("work-address")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Set<KeyValue<String, Integer>>> getAllAddress() {
        return ResponseDto.of(positionService.getWorkAddress(), 200);
    }
}
