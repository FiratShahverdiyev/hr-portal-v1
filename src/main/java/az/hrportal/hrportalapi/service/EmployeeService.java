package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.error.EntityNotFoundException;
import az.hrportal.hrportalapi.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Transactional
    public void updatePhoto(Integer id, String fileName) {
        log.info("updatePhoto service started with id : {}, fileName : {}", id, fileName);
        Employee employee = employeeRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException(Employee.class));
        employee.setPhoto(fileName);
        employeeRepository.save(employee);
        log.info("updatePhoto service completed with id : {}, fileName : {}", id, fileName);
    }
}
