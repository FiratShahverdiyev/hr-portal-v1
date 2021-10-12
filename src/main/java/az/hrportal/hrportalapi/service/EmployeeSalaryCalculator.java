package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.constant.Constant;
import az.hrportal.hrportalapi.constant.employee.Quota;
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
import java.util.ArrayList;
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
        calculateEmployeeWorkDay(now);
        setEmployeesMonthlySalary();
        log.info("********** salaryManager schedule completed **********");
    }

    @Transactional
    protected void calculateEmployeeWorkDay(LocalDate now) {
        log.info("calculateEmployeeWorkDay schedule started");
        List<EmployeeSalary> employeeSalaries = employeeSalaryRepository.findAllByBackupIsFalse();
        for (EmployeeSalary employeeSalary : employeeSalaries) {
            if (checkEmployeeOperations(employeeSalary.getEmployee().getOperations(), now))
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
        backup(now);
        List<EmployeeSalary> employees = employeeSalaryRepository.findAllByBackupIsFalse();
        calculateSalary(employees, now);
        log.info("********** setEmployeesMonthlySalary schedule completed **********");
    }

    public int getJobDayCountBetween(LocalDate from, LocalDate to) {
        int jobDayCount = 0;
        while (!from.isAfter(to)) {
            Day day = dayRepository.findByDay(from).orElseThrow(() ->
                    new EntityNotFoundException(Day.class, "getJobDayCountBetween"));
            if (day.isJobDay())
                jobDayCount++;
            from = from.plusDays(1);
        }
        return jobDayCount;
    }

    public void setEmployeeSalary(Employee employee, EmployeeSalaryResponseDto responseDto, LocalDate date) {
        float gross = employee.getSalary();
        float dsmf = percentage(gross, Constant.DSMF);
        float incomeTax = calculateIncomeTax(employee);
        float its = percentage(gross, Constant.ITS);
        float unemploymentInsurance = percentage(gross, Constant.UNEMPLOYMENT_INSURANCE);
        float tradeUnion = percentage(gross, Constant.TRADE_UNION);
        float totalTax = dsmf + incomeTax + its + unemploymentInsurance + tradeUnion;
        float catchAmount = 0f;
        if (employee.getCatchMonths().contains(date.getMonthValue())) {
            catchAmount = employee.getCatchAmount();
        }
        float netSalary = gross - totalTax;
        responseDto.setGrossSalary(gross);
        responseDto.setNetSalary(netSalary);
        responseDto.setEmployeeITS(its);
        responseDto.setEmployeeMDSS(percentage(gross, 3f));
        responseDto.setEmployeeUnemploymentTax(unemploymentInsurance);
        Integer dayCount = dayRepository.getJobDayCount(date.getMonthValue(), date.getYear());
        responseDto.setActiveDayCount(dayCount);
        responseDto.setEmployeeActiveDayCount(dayCount);
    }

    @Transactional
    protected void calculateSalary(List<EmployeeSalary> employees, LocalDate date) {
        log.info("calculateSalary service started");
        List<EmployeeSalary> employeeSalaries = new ArrayList<>();
        for (EmployeeSalary employee : employees) {
            float gross = employee.getGrossSalary();
            float dsmf = percentage(gross, Constant.DSMF);
            float incomeTax = calculateIncomeTax(employee.getEmployee());
            float its = percentage(gross, Constant.ITS);
            float unemploymentInsurance = percentage(gross, Constant.UNEMPLOYMENT_INSURANCE);
            float tradeUnion = percentage(gross, Constant.TRADE_UNION);
            float catchAmount = 0f;
            if (employee.getEmployee().getCatchMonths().contains(date.getMonthValue())) {
                catchAmount = employee.getEmployee().getCatchAmount();
            }
            float totalTax = dsmf + incomeTax + its + unemploymentInsurance + tradeUnion + catchAmount;
            float netSalary = gross - totalTax;
            employee.setGrossSalary(gross);
            employee.setDsmf(dsmf);
            employee.setIncomeTax(incomeTax);
            employee.setIts(its);
            employee.setUnemploymentInsurance(unemploymentInsurance);
            employee.setTradeUnion(tradeUnion);
            employee.setNetSalary(netSalary);
            employeeSalaries.add(employee);
        }
        employeeSalaryRepository.saveAll(employeeSalaries);
        log.info("********** calculateSalary service completed with **********");
    }

    private void backup(LocalDate now) {
        log.info("backup schedule started");
        Objects.requireNonNull(cacheManager.getCache("employee-salaries")).clear();
        List<EmployeeSalary> employeeSalaries = employeeSalaryRepository
                .findAllByCreateDate(now.getMonthValue());
        for (EmployeeSalary employeeSalary : employeeSalaries) {
            employeeSalary.setBackup(true);
        }
        employeeSalaryRepository.saveAll(employeeSalaries);
        log.info("********** backup schedule completed **********");
    }

    private boolean checkEmployeeOperations(Set<Operation> operations, LocalDate now) {
        for (Operation operation : operations) {
            if (Constant.passiveDayDocuments.contains(operation.getDocumentType())) {
                LocalDate from = operation.getEventFrom();
                LocalDate to = operation.getEventTo();
                if (from.isEqual(now) || from.isAfter(now) || to.isBefore(now) || to.isEqual(now))
                    return false;
            }
        }
        return true;
    }

    private float calculateIncomeTax(Employee employee) {
        boolean flag;
        float total;
        if (employee.getSalary() <= 2500) {
            total = 200;
            flag = false;
        } else {
            total = 2500;
            flag = true;
        }
        if (employee.getQuotas() != null)
            for (String quota : employee.getQuotas()) {
                if (quota.equals(Quota.getQuota(8)) || quota.equals(Quota.getQuota(9))) {
                    total += 400;
                    break;
                }
                if (quota.equals(Quota.getQuota(5)) || quota.equals(Quota.getQuota(3))) {
                    total += 200;
                    break;
                }
                if (quota.equals(Quota.getQuota(7))) {
                    total += 100;
                    break;
                }
            }
        float incomeTaxAmount = employee.getSalary() - total;
        if (!flag)
            return percentage(incomeTaxAmount >= 0 ? incomeTaxAmount : 0, Constant.LESS_THAN_2500_INCOME_TAX);
        else
            return percentage(incomeTaxAmount >= 0 ? incomeTaxAmount : 0,
                    Constant.MORE_THAN_2500_INCOME_TAX) + 350f;
    }

    private float percentage(float number, float percentage) {
        return number * percentage / 100;
    }
}
