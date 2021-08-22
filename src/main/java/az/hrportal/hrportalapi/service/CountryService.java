package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.domain.employee.Country;
import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import az.hrportal.hrportalapi.mapper.DropDownMapper;
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
    private final DropDownMapper dropDownMapper;

    public Integer save(String countryName) {
        log.info("save (Country) service started with country : {}", countryName);
        Country country = new Country();
        country.setName(countryName);
        Country saved = countryRepository.save(country);
        log.info("********** save (Country) service completed with country : {}, id : {} **********",
                countryName, saved.getId());
        return saved.getId();
    }

    public List<DropDownResponseDto<String>> getAll() {
        log.info("getAllCountries service started");
        List<DropDownResponseDto<String>> response = dropDownMapper.toCountryResponseDtos(countryRepository.findAll());
        log.info("********** getAllCountries service completed **********");
        return response;
    }
}
