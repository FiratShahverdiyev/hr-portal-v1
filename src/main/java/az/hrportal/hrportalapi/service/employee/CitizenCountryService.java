package az.hrportal.hrportalapi.service.employee;

import az.hrportal.hrportalapi.domain.employee.CitizenCountry;
import az.hrportal.hrportalapi.dto.DropDownResponseDto;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.mapper.DropDownMapper;
import az.hrportal.hrportalapi.repository.employee.CitizenCountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CitizenCountryService {
    private final CitizenCountryRepository citizenCountryRepository;
    private final DropDownMapper dropDownMapper;

    public Integer save(String countryName) {
        log.info("save (CitizenCountry) service started with country : {}", countryName);
        CitizenCountry citizenCountry = new CitizenCountry();
        citizenCountry.setName(countryName);
        CitizenCountry saved = citizenCountryRepository.save(citizenCountry);
        log.info("********** save (CitizenCountry) service completed with country : {}, id : {} **********",
                countryName, saved.getId());
        return saved.getId();
    }

    public List<DropDownResponseDto<String>> getAll() {
        log.info("getAll service started");
        List<DropDownResponseDto<String>> response = dropDownMapper
                .toCountryResponseDtos(citizenCountryRepository.findAll());
        log.info("********** getAll service completed **********");
        return response;
    }

    public String delete(String name) {
        log.info("delete (CitizenCountry) service started with name : {}", name);
        CitizenCountry citizenCountry = citizenCountryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(CitizenCountry.class, name));
        citizenCountryRepository.delete(citizenCountry);
        log.info("********** delete (CitizenCountry) service completed with name : {} **********", name);
        return name;
    }
}
