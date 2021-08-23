package az.hrportal.hrportalapi.service.employee;

import az.hrportal.hrportalapi.constant.employee.Quota;
import az.hrportal.hrportalapi.domain.employee.Country;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.employee.GovernmentAchievement;
import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.dto.employee.request.AcademicRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.BusinessRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.EmployeeGeneralInfoRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.GovernmentAchievementRequestDto;
import az.hrportal.hrportalapi.dto.employee.response.AcademicInfoResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.BusinessResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.EmployeeResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.GeneralInfoResponseDto;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.mapper.employee.EmployeeMapper;
import az.hrportal.hrportalapi.mapper.employee.EmployeeResponseMapper;
import az.hrportal.hrportalapi.mapper.employee.helper.EmployeeMapperHelper;
import az.hrportal.hrportalapi.repository.employee.CountryRepository;
import az.hrportal.hrportalapi.repository.employee.EmployeeRepository;
import az.hrportal.hrportalapi.repository.employee.GovernmentAchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final CountryRepository countryRepository;
    private final GovernmentAchievementRepository governmentAchievementRepository;
    private final EmployeeMapper employeeMapper;
    private final EmployeeResponseMapper employeeResponseMapper;
    private final EmployeeMapperHelper employeeMapperHelper;

    @Transactional
    public void setPhotoName(Integer id, String fileName) {
        log.info("setPhotoName service started with id : {}, fileName : {}", id, fileName);
        Employee employee = employeeRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException(Employee.class, id));
        employee.setPhoto(fileName);
        employeeRepository.save(employee);
        log.info("********** setPhotoName service completed with id : {}, fileName : {} **********", id, fileName);
    }

    @Transactional
    @SneakyThrows
    public Integer saveGeneralInfo(EmployeeGeneralInfoRequestDto employeeGeneralInfoRequestDto) {
        log.info("saveGeneralInfo service started with {}", employeeGeneralInfoRequestDto);
        Optional<Country> optionalCountry = countryRepository
                .findByName(employeeGeneralInfoRequestDto.getCitizenCountry());
        Country country;
        if (optionalCountry.isEmpty()) {
            country = saveOther(employeeGeneralInfoRequestDto.getCitizenCountry());
        } else {
            country = optionalCountry.get();
        }
        Employee employee = new Employee();
        employeeMapper.updateEmployee(employee, employeeGeneralInfoRequestDto);
        employee.setCitizenCountry(country);
        Employee saved = employeeRepository.save(employee);
        log.info("********** saveGeneralInfo service completed with id : {} **********", saved.getId());
        return saved.getId();
    }

    @Transactional
    @SneakyThrows
    public Integer updateGeneralInfo(Integer id, EmployeeGeneralInfoRequestDto employeeGeneralInfoRequestDto) {
        log.info("updateGeneralInfo service started with {}", employeeGeneralInfoRequestDto);
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Employee.class, id));
        Optional<Country> optionalCountry = countryRepository
                .findByName(employeeGeneralInfoRequestDto.getCitizenCountry());
        Country country;
        if (optionalCountry.isEmpty()) {
            country = saveOther(employeeGeneralInfoRequestDto.getCitizenCountry());
        } else {
            country = optionalCountry.get();
        }
        employeeMapper.updateEmployee(employee, employeeGeneralInfoRequestDto);
        employee.setCitizenCountry(country);
        Employee saved = employeeRepository.save(employee);
        log.info("********** updateGeneralInfo service completed with id : {} **********", saved.getId());
        return saved.getId();
    }


    @Transactional
    @SneakyThrows
    public Integer updateBusiness(Integer id, BusinessRequestDto businessRequestDto) {
        log.info("updateBusiness service started with {}", businessRequestDto);
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Employee.class, id));
        employeeMapper.updateEmployee(employee, businessRequestDto);
        Employee saved = employeeRepository.save(employee);
        log.info("********** updateBusiness service completed with id : {} **********", saved.getId());
        return saved.getId();
    }

    @Transactional
    @SneakyThrows
    public Integer updateAcademic(Integer id, AcademicRequestDto academicRequestDto) {
        log.info("updateAcademic service started with {}", academicRequestDto);
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Employee.class, id));
        employeeMapper.updateEmployee(employee, academicRequestDto);
        List<String> quotas = getQuotasByKeys(academicRequestDto.getQuotas());
        List<GovernmentAchievement> governmentAchievements =
                saveAndGetGovernmentAchievements(academicRequestDto.getGovernmentAchievements(), employee);
        employee.setGovernmentAchievements(new HashSet<>(governmentAchievements));
        employee.setQuotas(new HashSet<>(quotas));
        //S.S Sehadetnamesi nomresi
        Employee saved = employeeRepository.save(employee);
        log.info("********** updateAcademic service completed with id : {} **********", saved.getId());
        return saved.getId();
    }


    public GeneralInfoResponseDto getGeneralInfoById(Integer id) {
        log.info("getGeneralInfoById service started with id : {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Employee.class, id));
        log.info("********** getGeneralInfoById service completed with id : {} **********", id);
        return employeeResponseMapper.toGeneralInfoResponseDto(employee);
    }

    public BusinessResponseDto getBusinessInfoById(Integer id) {
        log.info("getBusinessInfoById service started with id : {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Employee.class, id));
        log.info("********** getBusinessInfoById service completed with id : {} **********", id);
        return employeeResponseMapper.toBusinessResponseDto(employee);
    }

    public AcademicInfoResponseDto getAcademicInfoById(Integer id) {
        log.info("getAcademicInfoById service started with id : {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Employee.class, id));
        log.info("********** getAcademicInfoById service completed with id : {} **********", id);
        return employeeResponseMapper.toAcademicInfoResponseDto(employee);
    }

    public List<String> getAllFullNameAndPosition() {
        log.info("getAllFullNameAndPosition service started");
        List<Employee> employees = employeeRepository.findAll();
        List<String> response = new ArrayList<>();
        for (Employee employee : employees) {
            String fullNameAndPosition = employee.getFullName().concat(" ")
                    .concat(employee.getBusinessPosition());
            response.add(fullNameAndPosition);
        }
        log.info("********** getAllFullNameAndPosition service started **********");
        return response;
    }

    public List<EmployeeResponseDto> getAll() {
        log.info("getAll service started");
        List<EmployeeResponseDto> response = employeeResponseMapper
                .toEmployeeResponseDtos(employeeRepository.findAll());
        log.info("********** getAll service completed **********");
        return response;
    }

    @Transactional
    public Integer delete(Integer id) {
        log.info("delete (Employee) service started with id : {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Employee.class, id));
        employeeRepository.delete(employee);
        log.info("********** delete (Employee) service completed with id : {} **********", id);
        return id;
    }

    //TODO Delete on production
    public List<KeyValue<String, Integer>> getAllQuotas() {
        log.info("getAllQuotas service started");
        List<KeyValue<String, Integer>> response = new ArrayList<>();
        for (Integer key : Quota.getQuotaMapKeySet()) {
            KeyValue<String, Integer> quota = new KeyValue<>(Quota.getQuota(key), key);
            response.add(quota);
        }
        log.info("********** getAllQuotas service completed **********");
        return response;
    }

    @Transactional
    protected Country saveOther(String name) {
        Country country = new Country();
        country.setName(name);
        return countryRepository.save(country);
    }

    @Transactional
    protected List<GovernmentAchievement> saveAndGetGovernmentAchievements(
            List<GovernmentAchievementRequestDto> governmentAchievementRequestDtos, Employee employee) {
        List<GovernmentAchievement> governmentAchievements = new ArrayList<>();
        for (GovernmentAchievementRequestDto requestDto : governmentAchievementRequestDtos) {
            Optional<GovernmentAchievement> optionalGovernmentAchievement = governmentAchievementRepository
                    .findByNameAndOrganization(requestDto.getName(), requestDto.getOrganization());
            GovernmentAchievement governmentAchievement;
            if (optionalGovernmentAchievement.isEmpty()) {
                governmentAchievement = employeeMapperHelper.toGovernmentAchievement(requestDto);
                governmentAchievement.setEmployee(employee);
                governmentAchievement = governmentAchievementRepository.save(governmentAchievement);
            } else {
                governmentAchievement = optionalGovernmentAchievement.get();
            }
            governmentAchievements.add(governmentAchievement);
        }
        return governmentAchievements;
    }

    private List<String> getQuotasByKeys(List<Integer> quotaKeys) {
        List<String> quotas = new ArrayList<>();
        for (Integer quotaKey : quotaKeys) {
            quotas.add(Quota.getQuota(quotaKey));
        }
        return quotas;
    }
}