package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.constant.employee.BloodGroup;
import az.hrportal.hrportalapi.constant.employee.EducationType;
import az.hrportal.hrportalapi.constant.employee.FamilyCondition;
import az.hrportal.hrportalapi.constant.employee.Gender;
import az.hrportal.hrportalapi.constant.employee.Quota;
import az.hrportal.hrportalapi.constant.employee.Series;
import az.hrportal.hrportalapi.domain.employee.Address;
import az.hrportal.hrportalapi.domain.employee.Business;
import az.hrportal.hrportalapi.domain.employee.ContactInfo;
import az.hrportal.hrportalapi.domain.employee.Country;
import az.hrportal.hrportalapi.domain.employee.Education;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.employee.ForeignPassport;
import az.hrportal.hrportalapi.domain.employee.GovernmentAchievement;
import az.hrportal.hrportalapi.domain.employee.IDCard;
import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.dto.employee.request.AcademicRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.BusinessRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.EmployeeGeneralInfoRequestDto;
import az.hrportal.hrportalapi.dto.employee.response.AcademicInfoResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.BusinessResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.GeneralInfoResponseDto;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.mapper.employee.AcademicInfoMapper;
import az.hrportal.hrportalapi.mapper.employee.BusinessInfoMapper;
import az.hrportal.hrportalapi.mapper.employee.CertificateMapper;
import az.hrportal.hrportalapi.mapper.employee.EmployeeGeneralInfoMapper;
import az.hrportal.hrportalapi.mapper.employee.FamilyMemberMapper;
import az.hrportal.hrportalapi.mapper.employee.GovernmentAchievementMapper;
import az.hrportal.hrportalapi.repository.employee.AddressRepository;
import az.hrportal.hrportalapi.repository.employee.BusinessRepository;
import az.hrportal.hrportalapi.repository.employee.ContactInfoRepository;
import az.hrportal.hrportalapi.repository.employee.CountryRepository;
import az.hrportal.hrportalapi.repository.employee.EducationRepository;
import az.hrportal.hrportalapi.repository.employee.EmployeeRepository;
import az.hrportal.hrportalapi.repository.employee.ForeignPassportRepository;
import az.hrportal.hrportalapi.repository.employee.GovernmentAchievementRepository;
import az.hrportal.hrportalapi.repository.employee.IDCardRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final CountryRepository countryRepository;
    private final AddressRepository addressRepository;
    private final ContactInfoRepository contactInfoRepository;
    private final ForeignPassportRepository foreignPassportRepository;
    private final IDCardRepository idCardRepository;
    private final BusinessRepository businessRepository;
    private final EducationRepository educationRepository;
    private final FamilyMemberMapper familyMemberMapper;
    private final GovernmentAchievementMapper governmentAchievementMapper;
    private final CertificateMapper certificateMapper;
    private final EmployeeMapper employeeMapper;
    private final EmployeeGeneralInfoMapper employeeGeneralInfoMapper;
    private final BusinessInfoMapper businessInfoMapper;
    private final GovernmentAchievementRepository governmentAchievementRepository;
    private final AcademicInfoMapper academicInfoMapper;

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Optional<Country> optionalCountry = countryRepository
                .findByName(employeeGeneralInfoRequestDto.getCitizenCountry());
        Country country;
        if (optionalCountry.isEmpty()) {
            country = saveOther(employeeGeneralInfoRequestDto.getCitizenCountry());
        } else {
            country = optionalCountry.get();
        }

        Employee employee = Employee.builder()
                .familyCondition(FamilyCondition.valueOf(employeeGeneralInfoRequestDto.getFamilyCondition()))
                .fullName(employeeGeneralInfoRequestDto.getFullName())
                .birthday(dateFormat.parse(employeeGeneralInfoRequestDto.getBirthday()))
                .birthplace(employeeGeneralInfoRequestDto.getBirthplace())
                .citizenCountry(country)
                .gender(Gender.valueOf(employeeGeneralInfoRequestDto.getGender()))
                .bloodGroup(BloodGroup.valueOf(employeeGeneralInfoRequestDto.getBloodGroup()))
                .permission(employeeGeneralInfoRequestDto.getPermission())
                .familyMembers(familyMemberMapper.toFamilyMembers(employeeGeneralInfoRequestDto.getFamilyMembers()))
                .build();
        Employee saved = employeeRepository.save(employee);

        Address address = Address.builder()
                .apartment(employeeGeneralInfoRequestDto.getAddressApartment())
                .block(employeeGeneralInfoRequestDto.getAddressBlock())
                .city(employeeGeneralInfoRequestDto.getAddressCity())
                .country(employeeGeneralInfoRequestDto.getAddressCountry())
                .district(employeeGeneralInfoRequestDto.getAddressDistrict())
                .home(employeeGeneralInfoRequestDto.getAddressHome())
                .street(employeeGeneralInfoRequestDto.getAddressStreet())
                .village(employeeGeneralInfoRequestDto.getAddressVillage())
                .employee(saved)
                .build();
        addressRepository.save(address);

        ContactInfo contactInfo = ContactInfo.builder()
                .businessMailAddress(employeeGeneralInfoRequestDto.getBusinessMailAddress())
                .businessPhone(employeeGeneralInfoRequestDto.getBusinessPhone())
                .homePhone(employeeGeneralInfoRequestDto.getHomePhone())
                .internalBusinessPhone(employeeGeneralInfoRequestDto.getInternalBusinessPhone())
                .mobilePhone1(employeeGeneralInfoRequestDto.getMobilePhone1())
                .mobilePhone2(employeeGeneralInfoRequestDto.getMobilePhone2())
                .ownMailAddress(employeeGeneralInfoRequestDto.getOwnMailAddress())
                .employee(employee)
                .build();
        contactInfoRepository.save(contactInfo);

        ForeignPassport foreignPassport = ForeignPassport.builder()
                .endDate(dateFormat.parse(employeeGeneralInfoRequestDto.getForeignPassportEndDate()))
                .series(Series.valueOf(employeeGeneralInfoRequestDto.getForeignPassportSeries()))
                .number(employeeGeneralInfoRequestDto.getForeignPassportNumber())
                .startDate(dateFormat.parse(employeeGeneralInfoRequestDto.getForeignPassportStartDate()))
                .employee(employee)
                .build();
        foreignPassportRepository.save(foreignPassport);

        IDCard idCard = IDCard.builder()
                .series(Series.valueOf(employeeGeneralInfoRequestDto.getIDCardSeries()))
                .endDate(dateFormat.parse(employeeGeneralInfoRequestDto.getIDCardEndDate()))
                .number(employeeGeneralInfoRequestDto.getIDCardNumber())
                .organization(employeeGeneralInfoRequestDto.getIDCardOrganization())
                .startDate(dateFormat.parse(employeeGeneralInfoRequestDto.getIDCardStartDate()))
                .employee(employee)
                .pin(employeeGeneralInfoRequestDto.getIDCardPin())
                .build();
        idCardRepository.save(idCard);

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        employee.setFamilyCondition(FamilyCondition.valueOf(employeeGeneralInfoRequestDto.getFamilyCondition()));
        employee.setFullName(employeeGeneralInfoRequestDto.getFullName());
        employee.setBirthday(dateFormat.parse(employeeGeneralInfoRequestDto.getBirthday()));
        employee.setBirthplace(employeeGeneralInfoRequestDto.getBirthplace());
        employee.setCitizenCountry(country);
        employee.setGender(Gender.valueOf(employeeGeneralInfoRequestDto.getGender()));
        employee.setBloodGroup(BloodGroup.valueOf(employeeGeneralInfoRequestDto.getBloodGroup()));
        employee.setPermission(employeeGeneralInfoRequestDto.getPermission());
        employee.setFamilyMembers(familyMemberMapper.toFamilyMembers(employeeGeneralInfoRequestDto.getFamilyMembers()));

        Address address = employee.getAddress();
        address.setApartment(employeeGeneralInfoRequestDto.getAddressApartment());
        address.setBlock(employeeGeneralInfoRequestDto.getAddressBlock());
        address.setCity(employeeGeneralInfoRequestDto.getAddressCity());
        address.setCountry(employeeGeneralInfoRequestDto.getAddressCountry());
        address.setDistrict(employeeGeneralInfoRequestDto.getAddressDistrict());
        address.setHome(employeeGeneralInfoRequestDto.getAddressHome());
        address.setStreet(employeeGeneralInfoRequestDto.getAddressStreet());
        address.setVillage(employeeGeneralInfoRequestDto.getAddressVillage());
        addressRepository.save(address);

        ContactInfo contactInfo = employee.getContactInfo();
        contactInfo.setBusinessMailAddress(employeeGeneralInfoRequestDto.getBusinessMailAddress());
        contactInfo.setBusinessPhone(employeeGeneralInfoRequestDto.getBusinessPhone());
        contactInfo.setHomePhone(employeeGeneralInfoRequestDto.getHomePhone());
        contactInfo.setInternalBusinessPhone(employeeGeneralInfoRequestDto.getInternalBusinessPhone());
        contactInfo.setMobilePhone1(employeeGeneralInfoRequestDto.getMobilePhone1());
        contactInfo.setMobilePhone2(employeeGeneralInfoRequestDto.getMobilePhone2());
        contactInfo.setOwnMailAddress(employeeGeneralInfoRequestDto.getOwnMailAddress());
        contactInfoRepository.save(contactInfo);

        ForeignPassport foreignPassport = employee.getForeignPassport();
        foreignPassport.setEndDate(dateFormat.parse(employeeGeneralInfoRequestDto.getForeignPassportEndDate()));
        foreignPassport.setSeries(Series.valueOf(employeeGeneralInfoRequestDto.getForeignPassportSeries()));
        foreignPassport.setNumber(employeeGeneralInfoRequestDto.getForeignPassportNumber());
        foreignPassport.setStartDate(dateFormat.parse(employeeGeneralInfoRequestDto.getForeignPassportStartDate()));
        foreignPassportRepository.save(foreignPassport);

        IDCard idCard = employee.getIdCard();
        idCard.setSeries(Series.valueOf(employeeGeneralInfoRequestDto.getIDCardSeries()));
        idCard.setEndDate(dateFormat.parse(employeeGeneralInfoRequestDto.getIDCardEndDate()));
        idCard.setNumber(employeeGeneralInfoRequestDto.getIDCardNumber());
        idCard.setOrganization(employeeGeneralInfoRequestDto.getIDCardOrganization());
        idCard.setStartDate(dateFormat.parse(employeeGeneralInfoRequestDto.getIDCardStartDate()));
        idCard.setPin(employeeGeneralInfoRequestDto.getIDCardPin());
        idCardRepository.save(idCard);

        Employee saved = employeeRepository.save(employee);

        log.info("********** updateGeneralInfo service completed with id : {} **********", saved.getId());
        return saved.getId();
    }

    @Transactional
    protected Country saveOther(String name) {
        Country country = new Country();
        country.setName(name);
        return countryRepository.save(country);
    }

    @Transactional
    @SneakyThrows
    public Integer updateBusiness(Integer id, BusinessRequestDto businessRequestDto) {
        log.info("updateBusiness service started with {}", businessRequestDto);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Employee.class, id));

        Business business = Business.builder()
                .company(businessRequestDto.getCompany())
                .employee(employee)
                .isMainJob(businessRequestDto.isMainJob())
                .jobEndDate(dateFormat.parse(businessRequestDto.getJobEndDate()))
                .jobStartDate(dateFormat.parse(businessRequestDto.getJobStartDate()))
                .jobEndReason(businessRequestDto.getJobEndReason())
                .position(businessRequestDto.getPosition())
                .section(businessRequestDto.getSection())
                .subSection(businessRequestDto.getSubSection())
                .build();
        businessRepository.save(business);

        employee.setBusiness(business);
        Employee saved = employeeRepository.save(employee);

        log.info("********** updateBusiness service completed with id : {} **********", saved.getId());
        return saved.getId();
    }

    @Transactional
    @SneakyThrows
    public Integer updateAcademic(Integer id, AcademicRequestDto academicRequestDto) {
        log.info("updateAcademic service started with {}", academicRequestDto);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Employee.class, id));

        Education education = Education.builder()
                .educationType(EducationType.valueOf(academicRequestDto.getEducationType()))
                .academicDegreeDate(dateFormat.parse(academicRequestDto.getAcademicDegreeDate()))
                .academicDegreeNumber(academicRequestDto.getAcademicDegreeNumber())
                .academicDegreeOrganization(academicRequestDto.getAcademicDegreeOrganization())
                .degree(academicRequestDto.getDegree())
                .direction(academicRequestDto.getDirection())
                .employee(employee)
                .entranceDate(dateFormat.parse(academicRequestDto.getEntranceDate()))
                .faculty(academicRequestDto.getFaculty())
                .graduateDate(dateFormat.parse(academicRequestDto.getGraduateDate()))
                .graduateFileNumber(academicRequestDto.getGraduateFileNumber())
                .graduateFileDate(dateFormat.parse(academicRequestDto.getGraduateFileDate()))
                .institution(academicRequestDto.getInstitution())
                .nostrifikasiyaNumber(academicRequestDto.getNostrifikasiyaNumber())
                .speciality(academicRequestDto.getSpeciality())
                .build();
        educationRepository.save(education);

        List<String> quotas = new ArrayList<>();
        for (Integer quotaKey : academicRequestDto.getQuotas()) {
            quotas.add(Quota.getQuota(quotaKey));
        }

        employee.setEducation(education);
        List<GovernmentAchievement> governmentAchievements = governmentAchievementMapper
                .toGovernmentAchievements(academicRequestDto.getGovernmentAchievements());
        for (GovernmentAchievement governmentAchievement : governmentAchievements) {
            governmentAchievement.setEmployee(employee);
        }
        governmentAchievementRepository.saveAll(governmentAchievements);
        employee.setGovernmentAchievements(governmentAchievements);
        employee.setCertificates(certificateMapper.toCertificates(academicRequestDto.getCertificates()));
        employee.setQuotas(quotas);
        employee.setMemberOfColleaguesAlliance(academicRequestDto.isMemberOfColleaguesAlliance());
        employee.setPrisoner(academicRequestDto.isPrisoner());
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
        return employeeGeneralInfoMapper.toGeneralInfoResponseDto(employee);
    }

    public BusinessResponseDto getBusinessInfoById(Integer id) {
        log.info("getBusinessInfoById service started with id : {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Employee.class, id));
        log.info("********** getBusinessInfoById service completed with id : {} **********", id);
        return businessInfoMapper.toBusinessResponseDto(employee);
    }

    public AcademicInfoResponseDto getAcademicInfoById(Integer id) {
        log.info("getAcademicInfoById service started with id : {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Employee.class, id));
        log.info("********** getAcademicInfoById service completed with id : {} **********", id);
        return academicInfoMapper.toAcademicInfoResponseDto(employee);
    }

    public List<String> getAllFullNameAndPosition() {
        log.info("getAllFullNameAndPosition service started");
        List<Employee> employees = employeeRepository.findAll();
        List<String> response = new ArrayList<>();
        for (Employee employee : employees) {
            String fullNameAndPosition = employee.getFullName().concat(" ")
                    .concat(employee.getBusiness().getPosition());
            response.add(fullNameAndPosition);
        }
        log.info("********** getAllFullNameAndPosition service started **********");
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
}
