package az.hrportal.hrportalapi.service.employee;

import az.hrportal.hrportalapi.domain.employee.AddressDistrict;
import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.repository.employee.AddressDistrictRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddressDistrictService {
    private final AddressDistrictRepository addressDistrictRepository;

    public Integer save(String districtName) {
        log.info("save (AddressDistrict) service started with district : {}", districtName);
        AddressDistrict addressDistrict = new AddressDistrict();
        addressDistrict.setName(districtName);
        AddressDistrict saved = addressDistrictRepository.save(addressDistrict);
        log.info("********** save (AddressDistrict) service completed with district : {}, id : {} **********",
                districtName, saved.getId());
        return saved.getId();
    }

    public Set<KeyValue<String, Integer>> getAll() {
        log.info("getAll service started");
        Set<KeyValue<String, Integer>> response = new HashSet<>();
        for (AddressDistrict addressDistrict : addressDistrictRepository.findAll()) {
            response.add(new KeyValue<>(addressDistrict.getName(), addressDistrict.getId()));
        }
        log.info("********** getAll service completed **********");
        return response;
    }
}
