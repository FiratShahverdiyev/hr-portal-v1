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
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class EmployeeSalaryCalculator {
    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final EmployeeRepository employeeRepository;
    private final DayRepository dayRepository;
    private final CacheManager cacheManager;

    @Scheduled(cron = "0 0 23 * * 1-5", zone = Constant.timeZone)
    private void salaryManager() {
        log.info("salaryManager schedule started");
        LocalDate now = LocalDate.now(ZoneId.of(Constant.timeZone));
        Day day = dayRepository.findByDay(now).orElseThrow(() -> new EntityNotFoundException(Day.class, now));
        if (!day.isJobDay()) {
            log.info("schedule ended because isn't job day. Date : {}", day.getDay());
            return;
        }
        calculateEmployeeWorkDay();
        setEmployeesMonthlySalary();
        log.info("********** salaryManager schedule completed **********");
    }

    @Transactional
    protected void calculateEmployeeWorkDay() {
        log.info("calculateEmployeeWorkDay schedule started");
        List<EmployeeSalary> employeeSalaries = employeeSalaryRepository.findAllByBackupIsFalse();
        for (EmployeeSalary employeeSalary : employeeSalaries) {
            if (checkEmployeeOperations(employeeSalary.getEmployee().getOperations()))
                employeeSalary.setActiveDays(employeeSalary.getActiveDays() + 1);
        }
        employeeSalaryRepository.saveAll(employeeSalaries);
        log.info("********** calculateEmployeeWorkDay schedule completed **********");
    }

    private void setEmployeesMonthlySalary() {
        log.info("setEmployeesMonthlySalary schedule started");
        LocalDate now = LocalDate.now(ZoneId.of(Constant.timeZone));
        Day day = dayRepository.findLastJobDayOfMonth(now.getMonthValue()).get(0);
        if (!day.getDay().isEqual(LocalDate.now())) {
            log.info("schedule ended because isn't last day of month. Date : {}", now);
            return;
        }
        backup();
        List<Employee> employees = employeeRepository.findAllByActiveIsTrue();
        for (Employee employee : employees) {
            calculate(employee);
        }
        log.info("********** setEmployeesMonthlySalary schedule completed **********");
    }

    public void setEmployeeSalary(Employee employee, EmployeeSalaryResponseDto responseDto) {
        Float gross = employee.getSalary();
        Float dsmf = percentage(gross, Constant.DSMF);
        Float incomeTax = percentage(gross, Constant.INCOME_TAX);
        Float its = percentage(gross, Constant.ITS);
        Float unemploymentInsurance = percentage(gross, Constant.UNEMPLOYMENT_INSURANCE);
        Float tradeUnion = percentage(gross, Constant.TRADE_UNION);
        Float totalTax = dsmf + incomeTax + its + unemploymentInsurance + tradeUnion;
        Float netSalary = gross - totalTax;
        responseDto.setGrossSalary(gross);
        responseDto.setNetSalary(netSalary);
    }

    @Transactional
    protected void calculate(Employee employee) {
        log.info("calculate service started");
        Float gross = employee.getSalary();
        Float dsmf = percentage(gross, Constant.DSMF);
        Float incomeTax = percentage(gross, Constant.INCOME_TAX);
        Float its = percentage(gross, Constant.ITS);
        Float unemploymentInsurance = percentage(gross, Constant.UNEMPLOYMENT_INSURANCE);
        Float tradeUnion = percentage(gross, Constant.TRADE_UNION);
        Float totalTax = dsmf + incomeTax + its + unemploymentInsurance + tradeUnion;
        Float netSalary = gross - totalTax;
        EmployeeSalary employeeSalary = EmployeeSalary.builder()
                .grossSalary(gross)
                .dsmf(dsmf)
                .incomeTax(incomeTax)
                .its(its)
                .unemploymentInsurance(unemploymentInsurance)
                .tradeUnion(tradeUnion)
                .netSalary(netSalary)
                .employee(employee)
                .build();
        EmployeeSalary saved = employeeSalaryRepository.save(employeeSalary);
        log.info("********** calculate service completed with savedId : {} **********", saved.getId());
    }

    private void backup() {
        log.info("backup schedule started");
        Objects.requireNonNull(cacheManager.getCache("employee-salaries")).clear();
        List<EmployeeSalary> employeeSalaries = employeeSalaryRepository.findAllByBackupIsFalse();
        for (EmployeeSalary employeeSalary : employeeSalaries) {
            employeeSalary.setBackup(true);
        }
        employeeSalaryRepository.saveAll(employeeSalaries);
        log.info("********** backup schedule completed **********");
    }

    private boolean checkEmployeeOperations(Set<Operation> operations) {
        for (Operation operation : operations) {
            if (Constant.passiveDayDocuments.contains(operation.getDocumentType()))
                return false;
        }
        return true;
    }

    private Float percentage(Float number, Float percentage) {
        return number * percentage / 100;
    }
}
