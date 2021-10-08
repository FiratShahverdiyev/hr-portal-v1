package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.constant.Constant;
import az.hrportal.hrportalapi.domain.Day;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.employee.EmployeeSalary;
import az.hrportal.hrportalapi.domain.operation.Operation;
import az.hrportal.hrportalapi.dto.employee.response.EmployeeSalaryResponseDto;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.repository.DayRepository;
import az.hrportal.hrportalapi.repository.employee.EmployeeRepository;
import az.hrportal.hrportalapi.repository.employee.EmployeeSalaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalaryCalculator {
    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final EmployeeRepository employeeRepository;
    private final DayRepository dayRepository;

    public List<EmployeeSalaryResponseDto> getAll() {
        log.info("getAll service started");
        List<Employee> employees = employeeRepository.findActiveEmployees();
        List<EmployeeSalaryResponseDto> response = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeSalaryResponseDto employeeSalaryResponseDto = new EmployeeSalaryResponseDto();
            employeeSalaryResponseDto.setId(employee.getId());
            employeeSalaryResponseDto.setFullName(employee.getFullName());
            EmployeeSalary employeeSalary = calculate(employee); //TODO maaslarin backup-ni al
            employeeSalaryResponseDto.setNetSalary(employeeSalary.getNetSalary());
            employeeSalaryResponseDto.setGrossSalary(employeeSalary.getGrossSalary());
            response.add(employeeSalaryResponseDto);
        }
        log.info("********** getAll service completed **********");
        return response;
    }

    @Transactional
    public EmployeeSalary calculate(Employee employee) {
        log.info("calculate service started");
        BigDecimal gross = employee.getSalary();
        BigDecimal dsmf = gross.multiply(BigDecimal.valueOf(Constant.DSMF));
        BigDecimal incomeTax = gross.multiply(BigDecimal.valueOf(Constant.INCOME_TAX));
        BigDecimal its = gross.multiply(BigDecimal.valueOf(Constant.ITS));
        BigDecimal unemploymentInsurance = gross.multiply(BigDecimal.valueOf(Constant.UNEMPLOYMENT_INSURANCE));
        BigDecimal tradeUnion = gross.multiply(BigDecimal.valueOf(Constant.TRADE_UNION));
        BigDecimal totalTax = dsmf.add(incomeTax).add(its).add(unemploymentInsurance).add(tradeUnion);
        BigDecimal netSalary = gross.subtract(totalTax);
        EmployeeSalary employeeSalary = EmployeeSalary.builder()
                .grossSalary(gross)
                .dsmf(dsmf)
                .incomeTax(incomeTax)
                .its(its)
                .unemploymentInsurance(unemploymentInsurance)
                .tradeUnion(tradeUnion)
                .netSalary(netSalary)
                .build();
        EmployeeSalary saved = employeeSalaryRepository.save(employeeSalary);
        log.info("********** calculate service completed with savedId : {} **********", saved.getId());
        return saved;
    }

    @Scheduled(cron = "0 23 * * *", zone = "Asia/Baku")
    public void dayManager() {
        log.info("dayManager schedule started");
        List<Employee> employees = employeeRepository.findActiveEmployees();
        LocalDate now = LocalDate.now();
        Day day = dayRepository.findByDay(now).orElseThrow(() -> new EntityNotFoundException(Day.class, now));
        if (day.isJobDay())
            return;
        for (Employee employee : employees) {
            EmployeeSalary employeeSalary = employeeSalaryRepository.getById(employee.getId());
            if (checkEmployeeOperations(employee.getOperations()))
                employeeSalary.setAcctiveDays(employeeSalary.getAcctiveDays() + 1);
        }
        log.info("********** dayManager schedule completed **********");
    }

    private boolean checkEmployeeOperations(Set<Operation> operations) {
        for (Operation operation : operations) {
            if (Constant.passiveDayDocuments.contains(operation.getDocumentType()))
                return false;
        }
        return true;
    }
}
