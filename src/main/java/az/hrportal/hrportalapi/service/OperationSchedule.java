package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.constant.Constant;
import az.hrportal.hrportalapi.constant.EmployeeActivity;
import az.hrportal.hrportalapi.constant.Status;
import az.hrportal.hrportalapi.constant.employee.Quota;
import az.hrportal.hrportalapi.domain.Day;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.employee.EmployeeSalary;
import az.hrportal.hrportalapi.domain.employee.GrossSalary;
import az.hrportal.hrportalapi.domain.operation.Operation;
import az.hrportal.hrportalapi.domain.position.Position;
import az.hrportal.hrportalapi.dto.employee.response.EmployeeSalaryResponseDto;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.repository.DayRepository;
import az.hrportal.hrportalapi.repository.GrossSalaryRepository;
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
    private final GrossSalaryRepository grossSalaryRepository;
    private final CacheManager cacheManager;

    @Scheduled(cron = "0 0 23 * * ?", zone = Constant.timeZone)
    @Transactional
    protected void schedule() {
        resetGrossCalculation();
        LocalDate now = LocalDate.now(ZoneId.of(Constant.timeZone));
        boolean activeDay = true;
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
                    //Additional salaryden vergi tutulurmu ? tutulmur
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
                            position.getAdditionalSalary() + operation.getNewOwnAdditionalSalary());
                    employee.setOwnAdditionalSalary(operation.getNewOwnAdditionalSalary());
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
                    employee.setGrossSalary(operation.getNewSalary()); //Bunda front newSalary gonderecek
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
                    Position position = operation.getPosition();
                    employee.setPosition(position);
                    employee.setGrossSalary(operation.getNewSalary() +
                            operation.getNewAdditionalSalary() + operation.getNewOwnAdditionalSalary());
                    employee.setOwnAdditionalSalary(operation.getNewOwnAdditionalSalary());
                    employeeRepository.save(employee);
                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case MUVEQQETI_KECIRILME: {
                    if (now.isBefore(operation.getChangeDate())) {
                        break;
                    }

                    Employee employee = operation.getEmployee();
                    Position position = operation.getPosition();
                    float newGross = position.getSalary().getAmount() +
                            position.getAdditionalSalary() + operation.getNewOwnAdditionalSalary();
                    int jobDayCount = dayRepository.getJobDayCount(now.getMonthValue(), now.getYear());
                    GrossSalary grossSalary = new GrossSalary();
                    if (employee.getGrossSalary() < newGross) {
                        grossSalary.setAmount(newGross / jobDayCount);
                    } else {
                        grossSalary.setAmount(employee.getGrossSalary() / jobDayCount);
                    }
                    grossSalary.setEmployee(employee);
                    grossSalary.setDate(now);
                    grossSalaryRepository.save(grossSalary);
                    employee.setGrossCalculated(true);
                    employeeRepository.save(employee);
                    if (now.equals(operation.getEventTo())) {
                        operation.setStatus(Status.DONE);
                        operationRepository.save(operation);
                    }
                    break;
                }
                case MUVEQQETI_HEVALE: {
                    if (now.isBefore(operation.getChangeDate())) {
                        break;
                    }
                    //Hevale muddeti (eventFrom - eventTo) erzinde eger diger iscinin emek haqqi coxdursa
                    //hemen tarix erzinde diger iscinin almali oldugu emek haqqi tapilir

                    if (now.equals(operation.getEventTo())) {
                        operation.setStatus(Status.DONE);
                        operationRepository.save(operation);
                    }
                    break;
                }
                case MUVEQQETI_EVEZETME: {
                    if (now.isBefore(operation.getChangeDate())) {
                        break;
                    }
                    int jobDayCount = dayRepository.getJobDayCount(now.getMonthValue(), now.getYear());
                    Employee employee = operation.getEmployee();
                    Position position = operation.getPosition();
                    float newSalary = position.getSalary().getAmount() + position.getAdditionalSalary();
                    GrossSalary grossSalary = new GrossSalary();
                    grossSalary.setDate(now);
                    grossSalary.setEmployee(employee);
                    grossSalary.setAmount((employee.getGrossSalary() / jobDayCount) + (newSalary / jobDayCount) / 2);
                    employee.setGrossCalculated(true);
                    employeeRepository.save(employee);
                    if (now.equals(operation.getEventTo())) {
                        operation.setStatus(Status.DONE);
                        operationRepository.save(operation);
                    }
                    break;
                }
                case MEZUNIYYET_VERILMESI: {
                    //Iscinin son 12 ay erzinde tam islediyi aylarin emek haqqisi toplanir bolunur sayina
                    //bolunur 30.4 e ve orta gunluk emek haqqi tapilir. (eventFrom - eventTo) erzinde hemen aya dusen
                    //gun sayina vurulur ve iscinin gorosu bolunur vurulur ve alinan reqemler
                    // toplanir iscinin grosu olur
                    //Qeyri is gunu , is gunu ferq etmir
                    Employee employee = operation.getEmployee();
                    float average = getAverageOfYear(employee);
                    int jobDayCount = dayRepository.getJobDayCount(now.getMonthValue(), now.getYear());
                    GrossSalary grossSalary = new GrossSalary();
                    grossSalary.setDate(now);
                    grossSalary.setEmployee(employee);
                    grossSalary.setAmount(employee.getGrossSalary() / jobDayCount + average);
                    employee.setGrossCalculated(true);
                    employeeRepository.save(employee);
                    if (now.equals(operation.getEventTo())) {
                        operation.setStatus(Status.DONE);
                        operationRepository.save(operation);
                    }
                    break;
                }
                case TELIME_GONDERILME:
                case ODENISHLI_ISTIRAHET_GUNU:
                case TEHSIL_YARADICILIQ_MEZUNIYYETI: {
                    //default 30 gun verilir. cixdigi vaxtdan 30 gun sonraya qeder maas hesablanmir ve son iki ay aldigi
                    //maas (hesablanan maas, yeni vergi tutulmamis) bolunur son iki ay islediyi gunlerin sayina vurulur
                    //hemen aya dusen mezuniyyet gunlerinin sayina
                    //is gunleri nezere alinir
                    Employee employee = operation.getEmployee();
                    Day date = dayRepository.findByDay(now).orElseThrow(() ->
                            new EntityNotFoundException(Day.class, now));
                    if (date.isJobDay()) {
                        GrossSalary grossSalary = new GrossSalary();
                        grossSalary.setEmployee(employee);
                        grossSalary.setDate(now);
                        grossSalary.setAmount(getAverageOfMonth(employee));
                    } else {
                        GrossSalary grossSalary = new GrossSalary();
                        grossSalary.setEmployee(employee);
                        grossSalary.setDate(now);
                        grossSalary.setAmount(0);
                    }
                    employee.setGrossCalculated(true);
                    employeeRepository.save(employee);
                    if (now.equals(operation.getEventTo())) {
                        operation.setStatus(Status.DONE);
                        operationRepository.save(operation);
                    }
                    break;
                }
                case ISCIYE_ODENISSIZ_MEZUNIYYET:
                case QISMEN_ODENISHLI_SOSIAL_MEZUNIYYET:
                case ISCIYE_SOSIAL_MEZUNIYYET: {
                    Employee employee = operation.getEmployee();
                    GrossSalary grossSalary = new GrossSalary();
                    grossSalary.setEmployee(employee);
                    grossSalary.setDate(now);
                    grossSalary.setAmount(0);
                    employee.setGrossCalculated(true);
                    employeeRepository.save(employee);
                    if (now.equals(operation.getEventTo())) {
                        operation.setStatus(Status.DONE);
                        operationRepository.save(operation);
                    }
                    break;
                }
                case MEZUNIYYETIN_UZADILMASI: {
                    //Mezuniyyetin vaxti uzadilir ve normal hesablanmalar davam edir

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case ODENISSIZ_MEZUNIYYETDEN_CAGIRILMA: {
                    //Iscinin mezuniyyet muddeti qisaldilir normal hesablamalar davam edir

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
                case EZAMIYYETE_GONDERILME: {
                    //Orta ayliq emek haqqi tapilir vurulur ezmamiyyetde oldugu muddete ve ezamiyyet (90 or 70) vurulur
                    //ezamiyyet muddetine alinan reqemler toplanir. Istirahet gunlerine dusse
                    // ise baslama tarixi uzadilir

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case EZAMIYYETIN_UZADILMASI: {
                    //Vaxt uzadilir yeniden hesbalanir

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case EZAMIYYETDEN_GERI_CAGIRILMA: {
                    //Vaxt qisalir yeniden hesablanir

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case MADDI_YARDIM: {
                    //Alacagi maasa elave olunur

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case MUKAFATLANDIRMA: {
                    //Alacagi maasa elave olunur

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case SHTAT_EMEK_HAQQINA_ELAVE: {
                    //Alacagi maasa elave olunur

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case SECKIDE_ISTIRAK: {
                    //Orta gunluk verilir

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case HERBI_CAGIRISH: {
                    //Orta gunluk verilir

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case TEDBIRDE_ISTIRAK: {
                    //Orta gunluk verilir

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case MUAVINETIN_TEYIN_OLUNMASI: {
                    //?

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case ISCININ_ISDEN_KENARLASDIRILMASI: {
                    //Maas verilmir

                    operation.setStatus(Status.DONE);
                    operationRepository.save(operation);
                    break;
                }
                case QEYRI_IS_GUNU: {
                    //+

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
                default: {

                }
            }
        }
        calculateNormalSalary(now);
        Day day = dayRepository.findLastJobDayOfMonth(now.getMonthValue()).get(0);
        if (!day.getDay().isEqual(LocalDate.now())) {
            log.info("schedule ended because isn't last day of month. Date : {}", now);
            return;
        }
        backup();
        calculateEmployeesMonthlySalary();
    }

    @Transactional
    protected void calculateNormalSalary(LocalDate now) {
        int jobDayCount = dayRepository.getJobDayCount(now.getMonthValue(), now.getYear());
        for (Employee employee : employeeRepository
                .findAllByGrossCalculatedIsFalseAndEmployeeActivity(EmployeeActivity.IN)) {
            GrossSalary grossSalary = new GrossSalary();
            grossSalary.setEmployee(employee);
            grossSalary.setAmount(employee.getGrossSalary() / jobDayCount);
            grossSalary.setDate(now);
            grossSalaryRepository.save(grossSalary);
        }
    }

    @Transactional
    protected void resetGrossCalculation() {
        for (Employee employee : employeeRepository.findAllByEmployeeActivity(EmployeeActivity.IN)) {
            employee.setGrossCalculated(false);
            employeeRepository.save(employee);
        }
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

    private void calculateEmployeesMonthlySalary() {
        log.info("calculateEmployeesMonthlySalary schedule started");
        for (Employee employee : employeeRepository.findAllByEmployeeActivity(EmployeeActivity.IN)) {
            float employeeMonthlyGross = 0f;
            EmployeeSalary employeeSalary = new EmployeeSalary();
            for (GrossSalary grossSalary : employee.getGrossSalaries()) {
                employeeMonthlyGross += grossSalary.getAmount();
            }
            grossSalaryRepository.deleteAll(employee.getGrossSalaries());
            employeeSalary.setEmployee(employee);
            employeeSalary.setGrossSalary(employeeMonthlyGross);
            //TODO calculate employee salary
            employeeSalaryRepository.save(employeeSalary);
        }
        log.info("********** calculateEmployeesMonthlySalary schedule completed **********");
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
        float gross = employee.getGrossSalary() / responseDto.getActiveDayCount() *
                responseDto.getEmployeeActiveDayCount();
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

    private void backup() {
        log.info("backup schedule started");
        Objects.requireNonNull(cacheManager.getCache("employee-salaries")).clear();
        List<EmployeeSalary> employeeSalaries = employeeSalaryRepository
                .findAllByBackupIsFalse();
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

    private float getAverageOfYear(Employee employee) {
        List<EmployeeSalary> employeeSalaries = employeeSalaryRepository
                .findAllByEmployeeAndBackupIsTrue(employee);
        int count = 0;
        float totalSalary = 0;
        int border = 12;
        for (EmployeeSalary employeeSalary : employeeSalaries) {
            int jobDayCount = dayRepository.getJobDayCount(employeeSalary.getSalaryCalculationDate()
                    .getMonthValue(), employeeSalary.getSalaryCalculationDate().getYear());
            if (jobDayCount == employeeSalary.getActiveDays()) {
                count++;
                totalSalary += employeeSalary.getGrossSalary();
            }
            if (border == 0)
                break;
            border--;
        }
        return totalSalary / count / 30.4f;
    }

    private float getAverageOfMonth(Employee employee) {
        List<EmployeeSalary> employeeSalaries = employeeSalaryRepository
                .findAllByEmployeeAndBackupIsTrue(employee);
        float totalSalary = 0;
        int border = 2;
        int totalActiveDays = 0;
        for (EmployeeSalary employeeSalary : employeeSalaries) {
            totalSalary += employeeSalary.getGrossSalary();
            totalActiveDays += employeeSalary.getActiveDays();
            if (border == 0)
                break;
            border--;
        }
        return totalSalary / totalActiveDays;
    }

    private float percentage(float number, float percentage) {
        return number * percentage / 100;
    }
}
