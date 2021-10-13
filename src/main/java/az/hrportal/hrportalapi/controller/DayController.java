package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.DayRequestDto;
import az.hrportal.hrportalapi.dto.DayResponseDto;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.service.DayService;
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

@RestController
@RequestMapping("day")
@RequiredArgsConstructor
public class DayController {
    private final DayService dayService;

    @PostMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public void initDayOfYear(@RequestParam String key) {
        dayService.initializeWorkDaysOfYear(key);
    }

    @GetMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<List<DayResponseDto>> getByYearAndMonth(@RequestParam int year, @RequestParam int month) {
        return ResponseDto.of(dayService.getAllByYearAndMonth(year, month), 200);
    }

    @PutMapping("{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<String> getByYearAndMonth(@PathVariable Integer id,
                                                 @RequestBody DayRequestDto dayRequestDto) {
        dayService.update(id, dayRequestDto);
        return ResponseDto.of("Successfully update", 200);
    }
}
