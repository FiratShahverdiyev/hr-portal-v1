package az.hrportal.hrportalapi.service.employee;

import az.hrportal.hrportalapi.domain.employee.AddressCity;
import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.repository.employee.AddressCityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddressCityService {
    private final AddressCityRepository addressCityRepository;

    public Integer save(String cityName) {
        log.info("save (AddressCity) service started with city : {}", cityName);
        AddressCity addressCity = new AddressCity();
        addressCity.setName(cityName);
        AddressCity saved = addressCityRepository.save(addressCity);
        log.info("********** save (AddressCity) service completed with city : {}, id : {} **********",
                cityName, saved.getId());
        return saved.getId();
    }

    public Set<KeyValue<String, Integer>> getAll() {
        log.info("getAll service started");
        Set<KeyValue<String, Integer>> response = new HashSet<>();
        for (AddressCity addressCity : addressCityRepository.findAll()) {
            response.add(new KeyValue<>(addressCity.getName(), addressCity.getId()));
        }
        log.info("********** getAll service completed **********");
        return response;
    }

    public Integer delete(Integer id) {
        log.info("delete (AddressCity) service started with id : {}", id);
        AddressCity addressCity = addressCityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(AddressCity.class, id));
        addressCityRepository.delete(addressCity);
        log.info("********** delete (AddressCity) service completed with id : {} **********", id);
        return id;
    }
}
