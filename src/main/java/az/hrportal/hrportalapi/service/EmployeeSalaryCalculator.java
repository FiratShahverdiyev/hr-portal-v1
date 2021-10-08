package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.constant.Constant;
import az.hrportal.hrportalapi.domain.Day;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.employee.EmployeeSalary;
import az.hrportal.hrportalapi.domain.operation.Operation;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.repository.DayRepository;
import az.hrportal.hrportalapi.repository.employee.EmployeeRepository;
import az.hrportal.hrportalapi.repository.employee.EmployeeSalaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class EmployeeSalaryCalculator {
    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final EmployeeRepository employeeRepository;
    private final DayRepository dayRepository;

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

    @Scheduled(cron = "0 0 23 * * *", zone = "Asia/Baku")
    private void dayManager() {
        log.info("dayManager schedule started");
        List<Employee> employees = employeeRepository.findAllByActiveIsTrue();
        LocalDate now = LocalDate.now();
        Day day = dayRepository.findByDay(now).orElseThrow(() -> new EntityNotFoundException(Day.class, now));
        if (day.isJobDay())
            return;
        for (Employee employee : employees) {
            EmployeeSalary employeeSalary = employeeSalaryRepository.getById(employee.getId());
            if (checkEmployeeOperations(employee.getOperations()))
                employeeSalary.setActiveDays(employeeSalary.getActiveDays() + 1);
        }
        setEmployeesMonthlySalary();
        log.info("********** dayManager schedule completed **********");
    }

    private void setEmployeesMonthlySalary() {
        log.info("setEmployeesMonthlySalary schedule started");
        Day lastDayOfMonth = dayRepository.findLastJobDayOfMonth(
                LocalDate.now().getMonthValue()).get(0);
        if (!lastDayOfMonth.getDay().isEqual(LocalDate.now()))
            return;
        backup();
        List<Employee> employees = employeeRepository.findAllByActiveIsTrue();
        for (Employee employee : employees) {
            calculate(employee);
        }
        log.info("********** setEmployeesMonthlySalary schedule completed **********");
    }

    private void backup() {
        log.info("backup schedule started");
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
