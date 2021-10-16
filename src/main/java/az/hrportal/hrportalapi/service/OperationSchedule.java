package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.constant.Constant;
import az.hrportal.hrportalapi.constant.EmployeeActivity;
import az.hrportal.hrportalapi.constant.Status;
import az.hrportal.hrportalapi.constant.employee.Quota;
import az.hrportal.hrportalapi.domain.Day;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.employee.EmployeeSalary;
import az.hrportal.hrportalapi.domain.operation.Operation;
import az.hrportal.hrportalapi.domain.position.Position;
import az.hrportal.hrportalapi.dto.employee.response.EmployeeSalaryResponseDto;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.repository.DayRepository;
import az.hrportal.hrportalapi.repository.OperationRepository;
import az.hrportal.hrportalapi.repository.employee.EmployeeRepository;
import az.hrportal.hrportalapi.repository.employee.EmployeeSalaryRepository;
import az.hrportal.hrportalapi.repository.position.PositionRepository;
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
public class OperationSchedule {
    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final EmployeeRepository employeeRepository;
    private final OperationRepository operationRepository;
    private final PositionRepository positionRepository;
    private final DayRepository dayRepository;
    private final CacheManager cacheManager;

    @Scheduled(cron = "0 0 23 * * ?", zone = Constant.timeZone)
    private void schedule() {
        LocalDate now = LocalDate.now(ZoneId.of(Constant.timeZone));
        for (Operation operation : operationRepository.findAllByStatus(Status.APPROVED)) {
            switch (operation.getDocumentType()) {
                case SHTAT_VAHIDININ_TESISI: {
                    Position position = operation.getPosition();
                    position.setStatus(Status.APPROVED);
                    positionRepository.save(position);
                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case SHTAT_VAHIDININ_LEGVI: {
                    Position position = operation.getPosition();
                    position.setStatus(Status.REJECTED);
                    positionRepository.save(position);
                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case ISHE_QEBUL: {
                    if (!now.equals(operation.getJoinDate())) {
                        break;
                    }
                    Employee employee = operation.getEmployee();
                    Position position = operation.getPosition();
                    employee.setEmployeeActivity(EmployeeActivity.IN);
                    //Additional salaryden vergi tutulurmu ?
                    employee.setGrossSalary(position.getSalary().getAmount() +
                            position.getAdditionalSalary() + operation.getOwnAdditionalSalary());
                    employee.setPosition(position);
                    employee.setJoinDate(operation.getJoinDate());
                    employee.setOwnAdditionalSalary(operation.getOwnAdditionalSalary());
                    employee.setWorkMode(position.getWorkMode());
                    //Sinaq muddetinin bir onemi varmi ?
                    employeeRepository.save(employee);
                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case XITAM: {
                    if (!now.equals(operation.getDismissalDate())) {
                        break;
                    }
                    Employee employee = operation.getEmployee();
                    employee.setEmployeeActivity(EmployeeActivity.OUT);
                    //Kompensasiya hesabla
                    employeeRepository.save(employee);
                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case VEZIFE_DEYISIKLIYI: {
                    if (!now.equals(operation.getChangeDate())) {
                        break;
                    }
                    Employee employee = operation.getEmployee();
                    Position position = operation.getPosition();
                    employee.setPosition(position);
                    employee.setGrossSalary(position.getSalary().getAmount() +
                            position.getAdditionalSalary() + operation.getOwnAdditionalSalary());
                    employee.setOwnAdditionalSalary(operation.getOwnAdditionalSalary());
                    employee.setWorkMode(position.getWorkMode());
                    employeeRepository.save(employee);
                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case EMEK_HAQQI_DEYISIKLIYI: {
                    if (!now.equals(operation.getChangeDate())) {
                        break;
                    }
                    Employee employee = operation.getEmployee();
                    employee.setGrossSalary(operation.getNewSalary() +
                            operation.getNewAdditionalSalary() + operation.getNewOwnAdditionalSalary());
                    employee.setOwnAdditionalSalary(operation.getNewOwnAdditionalSalary());
                    employeeRepository.save(employee);
                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case ELAVE_EMEK_HAQQI: {
                    if (!now.equals(operation.getChangeDate())) {
                        break;
                    }
                    Employee employee = operation.getEmployee();
                    employee.setGrossSalary(operation.getNewSalary() +
                            operation.getNewAdditionalSalary() + employee.getOwnAdditionalSalary());
                    employeeRepository.save(employee);
                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case ISH_REJIMININ_DEYISTIRILMESI: {
                    Employee employee = operation.getEmployee();
                    employee.setWorkMode(operation.getWorkMode());
                    //Sual Dəyişiklik edilən əmək haqqı Azn (vergilər və digər ödənişlər daxil olmaqla):
                    employeeRepository.save(employee);
                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case ISH_YERI_DEYISIKLIYI: {
                    if (!now.equals(operation.getChangeDate())) {
                        break;
                    }
                    Employee employee = operation.getEmployee();
                    //Bu nedir ?

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case MUVEQQETI_KECIRILME: {
                    if (!now.equals(operation.getChangeDate())) {
                        break;
                    }
                    //Sual pdfTemporaryPass izah lazimdi
                    Employee employee = operation.getEmployee();

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case MUVEQQETI_HEVALE: {
                    if (!now.equals(operation.getChangeDate())) {
                        break;
                    }
                    //Sual pdfTemporaryAssignment izah lazimdi
                    break;
                }
                case MUVEQQETI_EVEZETME: {
                    if (!now.equals(operation.getChangeDate())) {
                        break;
                    }
                    //Sual pdfTemporaryChange izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case MEZUNIYYET_VERILMESI: {
                    //Sual pdfGiveVacation izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case TEHSIL_YARADICILIQ_MEZUNIYYETI: {
                    //Sual pdfEducationVacation izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case ISCIYE_ODENISIZ_MEZUNIYYET: {
                    //Sual

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case ISCIYE_SOSIAL_MEZUNIYYET: {
                    //Sual pdfSocialVacation izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case QISMEN_ODENISHLI_SOSIAL_MEZUNIYYET: {
                    //Sual pdfPaidSocialVacation izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case ODENISHLI_ISTIRAHET_GUNU: {
                    //Sual pdfPaidDayOff izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case MEZUNIYYETIN_UZADILMASI: {
                    //Sual pdfSocialBusinessTrip izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case ODENISSIZ_MEZUNIYYETDEN_CAGIRILMA: {
                    //Sual pdfCallBackFromVacation izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case TELIM_PLANININ_TESDIQI:
                case MEZUNIYYET_QRAFIKININ_TESDIQI: {
                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case TELIME_GONDERILME: {
                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case EZAMIYYETE_GONDERILME: {
                    //Sual pdfGoOnBusinessTrip izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case EZAMIYYETIN_UZADILMASI: {
                    //Sual pdfIncreaseBusinessTrip izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case EZAMIYYETDEN_GERI_CAGIRILMA: {
                    //Sual pdfCallBackFromBusinessTrip izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case MADDI_YARDIM: {
                    //Sual pdfFinancialHelp izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case MUKAFATLANDIRMA: {
                    //Sual pdfAchievement izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case SHTAT_EMEK_HAQQINA_ELAVE: {
                    //Sual pdfPositionAdditionalSalary izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case SECKIDE_ISTIRAK: {
                    //Sual pdfEmployeeToSelection izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case TEDBIRDE_ISTIRAK: {
                    //Sual pdfAttendanceInTraining izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case ISCININ_ISDEN_KENARLASDIRILMASI: {
                    //Sual pdfAttendanceInTraining izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case QEYRI_IS_GUNU: {
                    //Sual pdfNonActiveDay izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case ISH_SAATININ_QISALDILMASI: {
                    //Sual pdfDecreaseWorkHours izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case XEBERDARLIQ: {
                    //Sual pdfWarning izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case EMEK_HAQQINDAN_TUTULMA: {
                    //Sual pdfCatchFromSalary izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case INTIZAM_TENBEHI: {
                    //Sual pdfDisciplineAction izah lazimdi

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
            }
        }
    }

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

    public int getJobDayCountBetween(LocalDate from, LocalDate to, LocalDate date) {
        int jobDayCount = 0;
        while (!from.isAfter(to)) {
            Day day = dayRepository.findByDay(from).orElseThrow(() ->
                    new EntityNotFoundException(Day.class, "getJobDayCountBetween"));
            if (day.isJobDay())
                jobDayCount++;
            from = from.plusDays(1);
            if (from.getMonthValue() != date.getMonthValue())
                break;
        }
        return jobDayCount;
    }

    public void setEmployeeSalary(Employee employee, EmployeeSalaryResponseDto responseDto, LocalDate date) {
        Integer dayCount = dayRepository.getJobDayCount(date.getMonthValue(), date.getYear());
        responseDto.setActiveDayCount(dayCount);
        responseDto.setEmployeeActiveDayCount(dayCount - checkEmployeeOperationsAndGetPassiveDayCount(employee
                .getOperations(), date));
        float gross = employee.getGrossSalary() / responseDto.getActiveDayCount() * responseDto.getEmployeeActiveDayCount();
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
        responseDto.setIncomingTax(incomeTax);
        responseDto.setPositionUnemploymentTax(unemploymentInsurance);
        responseDto.setEmployeeMDSS(percentage(gross, 3f));
        responseDto.setPositionMDSS(percentage(gross, 3f));
        responseDto.setEmployeeUnemploymentTax(unemploymentInsurance);
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

    private int checkEmployeeOperationsAndGetPassiveDayCount(Set<Operation> operations, LocalDate date) {
        int count = 0;
        for (Operation operation : operations) {
            if (operation.getStatus().equals(Status.APPROVED))
                if (Constant.passiveDayDocuments.contains(operation.getDocumentType())) {
                    LocalDate from = operation.getEventFrom();
                    LocalDate to = operation.getEventTo();
                    if (from == null || to == null)
                        continue;
                    count += getJobDayCountBetween(from, to, date);
                }
        }
        return count;
    }

    private float calculateIncomeTax(Employee employee) {
        boolean flag;
        float total;
        if (employee.getGrossSalary() <= 2500) {
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
        float incomeTaxAmount = employee.getGrossSalary() - total;
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
