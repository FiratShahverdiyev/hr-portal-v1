package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.constant.position.WorkPlace;
import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.repository.position.PositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;

    //TODO Delete on production
    public List<KeyValue<String, Integer>> getWorkAddress() {
        log.info("getWorkAddress service started");
        List<KeyValue<String, Integer>> response = new ArrayList<>();
        for (WorkPlace workPlaces : WorkPlace.values()) {
            KeyValue<String, Integer> keyValue = new KeyValue<>(workPlaces.toString(), workPlaces.getValue());
            response.add(keyValue);
        }
        log.info("********** getWorkAddress service completed **********");
        return response;
    }
}
