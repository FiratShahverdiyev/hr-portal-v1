package az.hrportal.hrportalapi.service.employee;

import az.hrportal.hrportalapi.domain.employee.AddressCountry;
import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.repository.employee.AddressCountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddressCountryService {
    private final AddressCountryRepository addressCountryRepository;

    public Integer save(String countryName) {
        log.info("save (AddressCountry) service started with country : {}", countryName);
        AddressCountry addressCountry = new AddressCountry();
        addressCountry.setName(countryName);
        AddressCountry saved = addressCountryRepository.save(addressCountry);
        log.info("********** save (AddressCountry) service completed with country : {}, id : {} **********",
                countryName, saved.getId());
        return saved.getId();
    }

    public Set<KeyValue<String, Integer>> getAll() {
        log.info("getAll service started");
        Set<KeyValue<String, Integer>> response = new HashSet<>();
        for (AddressCountry addressCountry : addressCountryRepository.findAll()) {
            response.add(new KeyValue<>(addressCountry.getName(), addressCountry.getId()));
        }
        log.info("********** getAll service completed **********");
        return response;
    }

    public Integer delete(Integer id) {
        log.info("delete (AddressCountry) service started with id : {}", id);
        AddressCountry addressCountry = addressCountryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(AddressCountry.class, id));
        addressCountryRepository.delete(addressCountry);
        log.info("********** delete (AddressCountry) service completed with id : {} **********", id);
        return id;
    }
}
