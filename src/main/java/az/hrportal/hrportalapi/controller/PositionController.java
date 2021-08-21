package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("position")
@RequiredArgsConstructor
public class PositionController {
    private final PositionService positionService;

    //TODO Delete on production
    @GetMapping("work-address")
    public ResponseDto<List<KeyValue<String, Integer>>> getAll() {
        return ResponseDto.of(positionService.getWorkAddress(), 200);
    }
}
