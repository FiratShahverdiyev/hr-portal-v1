package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.DayResponseDto;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.service.DayService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
