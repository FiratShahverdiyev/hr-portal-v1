package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.domain.employee.Country;
import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.repository.employee.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CountryService {
    private final CountryRepository countryRepository;

    public List<KeyValue<String, Integer>> getAllCountries() {
        log.info("getAllCountries service started");
        List<KeyValue<String, Integer>> response = new ArrayList<>();
        for (Country country : countryRepository.findAll()) {
            response.add(new KeyValue<>(country.getName(), country.getId()));
        }
        log.info("********** getAllCountries service completed **********");
        return response;
    }
}
