package az.hrportal.hrportalapi.service.employee;

import az.hrportal.hrportalapi.constant.employee.Quota;
import az.hrportal.hrportalapi.domain.employee.AddressCity;
import az.hrportal.hrportalapi.domain.employee.AddressCountry;
import az.hrportal.hrportalapi.domain.employee.AddressDistrict;
import az.hrportal.hrportalapi.domain.employee.CitizenCountry;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.employee.GovernmentAchievement;
import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.dto.PaginationResponseDto;
import az.hrportal.hrportalapi.dto.document.DocumentResponseDto;
import az.hrportal.hrportalapi.dto.employee.request.AcademicRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.BusinessRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.EmployeeGeneralInfoRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.GovernmentAchievementRequestDto;
import az.hrportal.hrportalapi.dto.employee.response.AcademicInfoResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.BusinessResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.EmployeeResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.GeneralInfoResponseDto;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.mapper.document.DocumentResponseMapper;
import az.hrportal.hrportalapi.mapper.employee.EmployeeMapper;
import az.hrportal.hrportalapi.mapper.employee.EmployeeResponseMapper;
import az.hrportal.hrportalapi.mapper.employee.helper.EmployeeMapperHelper;
import az.hrportal.hrportalapi.repository.employee.AddressCityRepository;
import az.hrportal.hrportalapi.repository.employee.AddressCountryRepository;
import az.hrportal.hrportalapi.repository.employee.AddressDistrictRepository;
import az.hrportal.hrportalapi.repository.employee.CitizenCountryRepository;
import az.hrportal.hrportalapi.repository.employee.EmployeeRepository;
import az.hrportal.hrportalapi.repository.employee.GovernmentAchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final CitizenCountryRepository citizenCountryRepository;
    private final AddressCityRepository addressCityRepository;
    private final AddressCountryRepository addressCountryRepository;
    private final AddressDistrictRepository addressDistrictRepository;
    private final GovernmentAchievementRepository governmentAchievementRepository;
    private final EmployeeMapper employeeMapper;
    private final EmployeeResponseMapper employeeResponseMapper;
    private final EmployeeMapperHelper employeeMapperHelper;
    private final DocumentResponseMapper documentResponseMapper;

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
        Optional<CitizenCountry> optionalCountry = citizenCountryRepository
                .findByName(employeeGeneralInfoRequestDto.getCitizenCountry());
        CitizenCountry citizenCountry;
        if (optionalCountry.isEmpty()) {
            citizenCountry = saveOther(employeeGeneralInfoRequestDto.getCitizenCountry());
        } else {
            citizenCountry = optionalCountry.get();
        }
        Employee employee = new Employee();
        employeeMapper.updateEmployee(employee, employeeGeneralInfoRequestDto);
        updateEmployeeAddress(employee, employeeGeneralInfoRequestDto.getAddressCountryId(),
                employeeGeneralInfoRequestDto.getAddressCityId(), employeeGeneralInfoRequestDto.getAddressDistrictId());
        employee.setCitizenCountry(citizenCountry);
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
        Optional<CitizenCountry> optionalCountry = citizenCountryRepository
                .findByName(employeeGeneralInfoRequestDto.getCitizenCountry());
        CitizenCountry citizenCountry;
        if (optionalCountry.isEmpty()) {
            citizenCountry = saveOther(employeeGeneralInfoRequestDto.getCitizenCountry());
        } else {
            citizenCountry = optionalCountry.get();
        }
        employeeMapper.updateEmployee(employee, employeeGeneralInfoRequestDto);
        updateEmployeeAddress(employee, employeeGeneralInfoRequestDto.getAddressCountryId(),
                employeeGeneralInfoRequestDto.getAddressCityId(), employeeGeneralInfoRequestDto.getAddressDistrictId());
        employee.setCitizenCountry(citizenCountry);
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

    public Set<KeyValue<String, Integer>> getAllFullNameAndPosition() {
        log.info("getAllFullNameAndPosition service started");
        List<Employee> employees = employeeRepository.findAll();
        Set<KeyValue<String, Integer>> response = new HashSet<>();
        for (Employee employee : employees) {
            String fullNameAndPosition = employee.getFullName();
            if (employee.getBusinessPosition() != null)
                fullNameAndPosition = fullNameAndPosition.concat(" ").concat(employee.getBusinessPosition());
            response.add(new KeyValue<>(fullNameAndPosition, employee.getId()));
        }
        log.info("********** getAllFullNameAndPosition service completed **********");
        return response;
    }

    public PaginationResponseDto<List<EmployeeResponseDto>> getPagination(int page, int size) {
        log.info("getPagination service started");
        List<EmployeeResponseDto> employees = employeeResponseMapper
                .toEmployeeResponseDtos(employeeRepository.findAll());
        PagedListHolder<EmployeeResponseDto> responseHolder = new PagedListHolder<>(employees);
        responseHolder.setPage(page);
        responseHolder.setPageSize(size);
        List<EmployeeResponseDto> response = responseHolder.getPageList();
        log.info("********** getPagination service completed **********");
        return new PaginationResponseDto<>(response, response.size(), employees.size());
    }

    public PaginationResponseDto<List<DocumentResponseDto>> getDocumentsById(Integer id, int page, int size) {
        log.info("getDocumentsById service started");
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Employee.class, id));
        List<DocumentResponseDto> documents = documentResponseMapper
                .toDocumentResponseDtos(new ArrayList<>(employee.getOperations()));
        PagedListHolder<DocumentResponseDto> responseHolder = new PagedListHolder<>(documents);
        responseHolder.setPageSize(page);
        responseHolder.setPageSize(size);
        List<DocumentResponseDto> response = responseHolder.getPageList();
        log.info("********** getDocumentsById service completed **********");
        return new PaginationResponseDto<>(response, response.size(), documents.size());
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
    public Set<KeyValue<String, Integer>> getAllQuotas() {
        log.info("getAllQuotas service started");
        Set<KeyValue<String, Integer>> response = new HashSet<>();
        for (Integer key : Quota.getQuotaMapKeySet()) {
            response.add(new KeyValue<>(Quota.getQuota(key), key));
        }
        log.info("********** getAllQuotas service completed **********");
        return response;
    }

    @Transactional
    protected CitizenCountry saveOther(String name) {
        log.info("saveOther service started with name : {}", name);
        CitizenCountry citizenCountry = new CitizenCountry();
        citizenCountry.setName(name);
        citizenCountry = citizenCountryRepository.save(citizenCountry);
        log.info("********** saveOther service completed with name : {} **********", name);
        return citizenCountry;
    }

    @Transactional
    protected List<GovernmentAchievement> saveAndGetGovernmentAchievements(
            List<GovernmentAchievementRequestDto> governmentAchievementRequestDtos, Employee employee) {
        log.info("saveAndGetGovernmentAchievements service started with {}", governmentAchievementRequestDtos);
        List<GovernmentAchievement> governmentAchievements = new ArrayList<>();
        for (GovernmentAchievementRequestDto requestDto : governmentAchievementRequestDtos) {
            Optional<GovernmentAchievement> optionalGovernmentAchievement = governmentAchievementRepository
                    .findByNameAndOrganization(requestDto.getName(), requestDto.getOrganization());
            GovernmentAchievement governmentAchievement;
            if (optionalGovernmentAchievement.isEmpty()) {
                governmentAchievement = employeeMapperHelper.toGovernmentAchievement(requestDto);
                governmentAchievement.getEmployees().add((employee));
                governmentAchievement = governmentAchievementRepository.save(governmentAchievement);
            } else {
                governmentAchievement = optionalGovernmentAchievement.get();
                governmentAchievement.setStartDate(LocalDate.parse(requestDto.getStartDate(),
                        DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                governmentAchievementRepository.save(governmentAchievement);
            }
            governmentAchievements.add(governmentAchievement);
        }
        log.info("********** saveAndGetGovernmentAchievements service completed with {} **********",
                governmentAchievementRequestDtos);
        return governmentAchievements;
    }

    private void updateEmployeeAddress(Employee employee, Integer addressCountryId, Integer addressCityId,
                                       Integer addressDistrictId) {
        AddressCountry addressCountry = addressCountryRepository.findById(addressCountryId).orElseThrow(() ->
                new EntityNotFoundException(AddressCountry.class, addressCountryId));
        AddressCity addressCity = addressCityRepository.findById(addressCityId).orElseThrow(() ->
                new EntityNotFoundException(AddressCity.class, addressCityId));
        AddressDistrict addressDistrict = addressDistrictRepository.findById(addressDistrictId).orElseThrow(() ->
                new EntityNotFoundException(AddressDistrict.class, addressDistrictId));
        employee.setAddressCountry(addressCountry);
        employee.setAddressCity(addressCity);
        employee.setAddressDistrict(addressDistrict);
    }

    private List<String> getQuotasByKeys(List<Integer> quotaKeys) {
        List<String> quotas = new ArrayList<>();
        for (Integer quotaKey : quotaKeys) {
            quotas.add(Quota.getQuota(quotaKey));
        }
        return quotas;
    }
}
