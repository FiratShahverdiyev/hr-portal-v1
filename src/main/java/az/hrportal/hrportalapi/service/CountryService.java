package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.dto.employee.response.CountryResponseDto;
import az.hrportal.hrportalapi.mapper.CountryMapper;
import az.hrportal.hrportalapi.repository.employee.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public List<CountryResponseDto> getAllCountries() {
        log.info("getAllCountries service started");
        List<CountryResponseDto> response = countryMapper.toCountryResponseDtos(countryRepository.findAll());
        log.info("********** getAllCountries service completed **********");
        return response;
    }
}
