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
import az.hrportal.hrportalapi.domain.employee.IDCard;
import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.dto.employee.request.AcademicRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.BusinessRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.GeneralInfoRequestDto;
import az.hrportal.hrportalapi.dto.employee.response.BusinessResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.GeneralInfoResponseDto;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.mapper.employee.BusinessInfoMapper;
import az.hrportal.hrportalapi.mapper.employee.CertificateMapper;
import az.hrportal.hrportalapi.mapper.employee.EmployeeMapper;
import az.hrportal.hrportalapi.mapper.employee.FamilyMemberMapper;
import az.hrportal.hrportalapi.mapper.employee.EmployeeGeneralInfoMapper;
import az.hrportal.hrportalapi.mapper.employee.GovernmentAchievementMapper;
import az.hrportal.hrportalapi.repository.employee.AddressRepository;
import az.hrportal.hrportalapi.repository.employee.BusinessRepository;
import az.hrportal.hrportalapi.repository.employee.ContactInfoRepository;
import az.hrportal.hrportalapi.repository.employee.CountryRepository;
import az.hrportal.hrportalapi.repository.employee.EducationRepository;
import az.hrportal.hrportalapi.repository.employee.EmployeeRepository;
import az.hrportal.hrportalapi.repository.employee.ForeignPassportRepository;
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
    public Integer saveGeneralInfo(GeneralInfoRequestDto generalInfoRequestDto) {
        log.info("saveGeneralInfo service started with {}", generalInfoRequestDto);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Optional<Country> optionalCountry = countryRepository.findByName(generalInfoRequestDto.getCitizenCountry());
        Country country;
        if (optionalCountry.isEmpty()) {
            country = saveOther(generalInfoRequestDto.getCitizenCountry());
        } else {
            country = optionalCountry.get();
        }

        Employee employee = Employee.builder()
                .familyCondition(FamilyCondition.intToEnum(generalInfoRequestDto.getFamilyCondition()))
                .fullName(generalInfoRequestDto.getFullName())
                .birthday(dateFormat.parse(generalInfoRequestDto.getBirthday()))
                .birthplace(generalInfoRequestDto.getBirthplace())
                .citizenCountry(country)
                .gender(Gender.intToEnum(generalInfoRequestDto.getGender()))
                .bloodGroup(BloodGroup.intToEnum(generalInfoRequestDto.getBloodGroup()))
                .permission(generalInfoRequestDto.getPermission())
                .familyMembers(familyMemberMapper.toFamilyMembers(generalInfoRequestDto.getFamilyMembers()))
                .build();
        Employee saved = employeeRepository.save(employee);

        Address address = Address.builder()
                .apartment(generalInfoRequestDto.getAddressApartment())
                .block(generalInfoRequestDto.getAddressBlock())
                .city(generalInfoRequestDto.getAddressCity())
                .country(generalInfoRequestDto.getAddressCountry())
                .district(generalInfoRequestDto.getAddressDistrict())
                .home(generalInfoRequestDto.getAddressHome())
                .street(generalInfoRequestDto.getAddressStreet())
                .village(generalInfoRequestDto.getAddressVillage())
                .employee(saved)
                .build();
        addressRepository.save(address);

        ContactInfo contactInfo = ContactInfo.builder()
                .businessMailAddress(generalInfoRequestDto.getBusinessMailAddress())
                .businessPhone(generalInfoRequestDto.getBusinessPhone())
                .homePhone(generalInfoRequestDto.getHomePhone())
                .internalBusinessPhone(generalInfoRequestDto.getInternalBusinessPhone())
                .mobilePhone1(generalInfoRequestDto.getMobilePhone1())
                .mobilePhone2(generalInfoRequestDto.getMobilePhone2())
                .ownMailAddress(generalInfoRequestDto.getOwnMailAddress())
                .employee(employee)
                .build();
        contactInfoRepository.save(contactInfo);

        ForeignPassport foreignPassport = ForeignPassport.builder()
                .endDate(dateFormat.parse(generalInfoRequestDto.getForeignPassportEndDate()))
                .series(Series.intToEnum(generalInfoRequestDto.getForeignPassportSeries()))
                .number(generalInfoRequestDto.getForeignPassportNumber())
                .startDate(dateFormat.parse(generalInfoRequestDto.getForeignPassportStartDate()))
                .employee(employee)
                .build();
        foreignPassportRepository.save(foreignPassport);

        IDCard idCard = IDCard.builder()
                .series(Series.intToEnum(generalInfoRequestDto.getIDCardSeries()))
                .endDate(dateFormat.parse(generalInfoRequestDto.getIDCardEndDate()))
                .number(generalInfoRequestDto.getIDCardNumber())
                .organization(generalInfoRequestDto.getIDCardOrganization())
                .startDate(dateFormat.parse(generalInfoRequestDto.getIDCardStartDate()))
                .employee(employee)
                .pin(generalInfoRequestDto.getIDCardPin())
                .build();
        idCardRepository.save(idCard);

        log.info("********** saveGeneralInfo service completed with id : {} **********", saved.getId());
        return saved.getId();
    }

    @Transactional
    @SneakyThrows
    public Integer updateGeneralInfo(Integer id, GeneralInfoRequestDto generalInfoRequestDto) {
        log.info("updateGeneralInfo service started with {}", generalInfoRequestDto);

        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Employee.class, id));

        Optional<Country> optionalCountry = countryRepository.findByName(generalInfoRequestDto.getCitizenCountry());
        Country country;
        if (optionalCountry.isEmpty()) {
            country = saveOther(generalInfoRequestDto.getCitizenCountry());
        } else {
            country = optionalCountry.get();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        employee.setFamilyCondition(FamilyCondition.intToEnum(generalInfoRequestDto.getFamilyCondition()));
        employee.setFullName(generalInfoRequestDto.getFullName());
        employee.setBirthday(dateFormat.parse(generalInfoRequestDto.getBirthday()));
        employee.setBirthplace(generalInfoRequestDto.getBirthplace());
        employee.setCitizenCountry(country);
        employee.setGender(Gender.intToEnum(generalInfoRequestDto.getGender()));
        employee.setBloodGroup(BloodGroup.intToEnum(generalInfoRequestDto.getBloodGroup()));
        employee.setPermission(generalInfoRequestDto.getPermission());
        employee.setFamilyMembers(familyMemberMapper.toFamilyMembers(generalInfoRequestDto.getFamilyMembers()));

        Address address = employee.getAddress();
        address.setApartment(generalInfoRequestDto.getAddressApartment());
        address.setBlock(generalInfoRequestDto.getAddressBlock());
        address.setCity(generalInfoRequestDto.getAddressCity());
        address.setCountry(generalInfoRequestDto.getAddressCountry());
        address.setDistrict(generalInfoRequestDto.getAddressDistrict());
        address.setHome(generalInfoRequestDto.getAddressHome());
        address.setStreet(generalInfoRequestDto.getAddressStreet());
        address.setVillage(generalInfoRequestDto.getAddressVillage());
        addressRepository.save(address);

        ContactInfo contactInfo = employee.getContactInfo();
        contactInfo.setBusinessMailAddress(generalInfoRequestDto.getBusinessMailAddress());
        contactInfo.setBusinessPhone(generalInfoRequestDto.getBusinessPhone());
        contactInfo.setHomePhone(generalInfoRequestDto.getHomePhone());
        contactInfo.setInternalBusinessPhone(generalInfoRequestDto.getInternalBusinessPhone());
        contactInfo.setMobilePhone1(generalInfoRequestDto.getMobilePhone1());
        contactInfo.setMobilePhone2(generalInfoRequestDto.getMobilePhone2());
        contactInfo.setOwnMailAddress(generalInfoRequestDto.getOwnMailAddress());
        contactInfoRepository.save(contactInfo);

        ForeignPassport foreignPassport = employee.getForeignPassport();
        foreignPassport.setEndDate(dateFormat.parse(generalInfoRequestDto.getForeignPassportEndDate()));
        foreignPassport.setSeries(Series.intToEnum(generalInfoRequestDto.getForeignPassportSeries()));
        foreignPassport.setNumber(generalInfoRequestDto.getForeignPassportNumber());
        foreignPassport.setStartDate(dateFormat.parse(generalInfoRequestDto.getForeignPassportStartDate()));
        foreignPassportRepository.save(foreignPassport);

        IDCard idCard = employee.getIdCard();
        idCard.setSeries(Series.intToEnum(generalInfoRequestDto.getIDCardSeries()));
        idCard.setEndDate(dateFormat.parse(generalInfoRequestDto.getIDCardEndDate()));
        idCard.setNumber(generalInfoRequestDto.getIDCardNumber());
        idCard.setOrganization(generalInfoRequestDto.getIDCardOrganization());
        idCard.setStartDate(dateFormat.parse(generalInfoRequestDto.getIDCardStartDate()));
        idCard.setPin(generalInfoRequestDto.getIDCardPin());
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
                .educationType(EducationType.intToEnum(academicRequestDto.getEducationType()))
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
        employee.setGovernmentAchievements(governmentAchievementMapper
                .toGovernmentAchievements(academicRequestDto.getGovernmentAchievements()));
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
