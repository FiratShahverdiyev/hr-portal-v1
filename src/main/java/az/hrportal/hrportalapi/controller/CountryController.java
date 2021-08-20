package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.service.CountryService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("country")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    @GetMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<List<KeyValue<String, Integer>>> getAll() {
        return ResponseDto.of(countryService.getAllCountries(), 200);
    }
}
