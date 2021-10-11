package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.dto.PaginationResponseDto;
import az.hrportal.hrportalapi.dto.employee.EmployeeSalaryData;
import az.hrportal.hrportalapi.dto.employee.response.EmployeeSalaryResponseDto;
import az.hrportal.hrportalapi.mapper.employee.EmployeeMapper;
import az.hrportal.hrportalapi.mapper.employee.EmployeeSalaryMapper;
import az.hrportal.hrportalapi.repository.employee.EmployeeRepository;
import az.hrportal.hrportalapi.repository.employee.EmployeeSalaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeSalaryService {
    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final EmployeeSalaryMapper employeeSalaryMapper;
    private final EmployeeRepository employeeRepository;
    private final EmployeeSalaryCalculator employeeSalaryCalculator;
    private final EntityManager entityManager;
    private final EmployeeMapper employeeMapper;

    @Cacheable("employee-salaries")
    public PaginationResponseDto<List<EmployeeSalaryResponseDto>> getAll(int page, int size) {
        log.info("getAll service started");
        List<EmployeeSalaryResponseDto> data = employeeSalaryMapper
                .toEmployeeSalaryResponseDtos(employeeSalaryRepository.findAllByBackupIsFalse());
        PagedListHolder<EmployeeSalaryResponseDto> listHolder = new PagedListHolder<>(data);
        listHolder.setPage(page);
        listHolder.setPageSize(size);
        List<EmployeeSalaryResponseDto> response = listHolder.getPageList();
        log.info("********** getAll service completed **********");
        return new PaginationResponseDto<>(response, response.size(), data.size());
    }

    public PaginationResponseDto<List<EmployeeSalaryResponseDto>> calculateAndGetAll(int page, int size) {
        log.info("calculateAndGetAll service started");
     /* String[] sortParams = new String[1];
        sortParams[0] = "fullName";
        Pageable pageable = new OffsetBasedPageRequest(page, size, "DESC", sortParams);
        Page<Employee> employees = employeeRepository.findAllByActiveIsTrue(pageable);*/
        List<EmployeeSalaryData> employees = entityManager
                .createQuery("SELECT new EmployeeSalaryData(e.id,e.fullName,e.position,e.salary,e.quotas)" +
                        " FROM Employee e left outer join  e.quotas", EmployeeSalaryData.class)
                .setMaxResults(size)
                .setFirstResult(page * size)
                .getResultList();
        List<EmployeeSalaryResponseDto> data = new ArrayList<>();
        for (EmployeeSalaryData employee : employees) {
            EmployeeSalaryResponseDto employeeSalaryResponseDto = new EmployeeSalaryResponseDto();
            employeeSalaryResponseDto.setFullName(employee.getFullName());
            employeeSalaryResponseDto.setId(employee.getId());
            if (employee.getPosition() != null)
                employeeSalaryResponseDto.setVacancyName(employee.getPosition().getVacancy().getName());
            employeeSalaryCalculator.setEmployeeSalary(employeeMapper.toEmployee(employee), employeeSalaryResponseDto);
            data.add(employeeSalaryResponseDto);
        }
        log.info("********** calculateAndGetAll service completed **********");
        return new PaginationResponseDto<>(data, data.size(), employeeRepository.getActiveEmployeeCount());
    }
}
