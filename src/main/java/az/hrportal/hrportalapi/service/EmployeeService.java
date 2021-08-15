package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.repository.EmployeeRepository;
import az.hrportal.hrportalapi.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;


}
