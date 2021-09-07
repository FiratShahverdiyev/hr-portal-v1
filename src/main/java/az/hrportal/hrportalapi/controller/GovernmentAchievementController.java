package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.service.employee.GovernmentAchievementService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("government-achievement")
@RequiredArgsConstructor
public class GovernmentAchievementController {
    private final GovernmentAchievementService governmentAchievementService;

    @GetMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<List<DropDownResponseDto<String>>> getAll() {
        return ResponseDto.of(governmentAchievementService.getAll(), 200);
    }

    @GetMapping("organizations")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<List<DropDownResponseDto<String>>> getOrganizations() {
        return ResponseDto.of(governmentAchievementService.getOrganizations(), 200);
    }
}
