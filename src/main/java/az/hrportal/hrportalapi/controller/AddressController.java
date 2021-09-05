package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.service.employee.AddressCityService;
import az.hrportal.hrportalapi.service.employee.AddressCountryService;
import az.hrportal.hrportalapi.service.employee.AddressDistrictService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressCityService addressCityService;
    private final AddressCountryService addressCountryService;
    private final AddressDistrictService addressDistrictService;

    @PostMapping("district")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> createDistrict(String district) {
        return ResponseDto.of(addressDistrictService.save(district), 200);
    }

    @GetMapping("district")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Set<KeyValue<String, Integer>>> getDistricts() {
        return ResponseDto.of(addressDistrictService.getAll(), 200);
    }

    @PostMapping("country")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> createCountry(String country) {
        return ResponseDto.of(addressCountryService.save(country), 200);
    }

    @GetMapping("country")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Set<KeyValue<String, Integer>>> getCountries() {
        return ResponseDto.of(addressCountryService.getAll(), 200);
    }

    @PostMapping("city")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> createCity(String city) {
        return ResponseDto.of(addressCityService.save(city), 200);
    }

    @GetMapping("city")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Set<KeyValue<String, Integer>>> getCities() {
        return ResponseDto.of(addressCityService.getAll(), 200);
    }
}
