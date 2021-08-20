package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.constant.employee.BloodGroup;
import az.hrportal.hrportalapi.constant.employee.EducationType;
import az.hrportal.hrportalapi.constant.employee.FamilyCondition;
import az.hrportal.hrportalapi.constant.employee.Gender;
import az.hrportal.hrportalapi.constant.employee.Kvota;
import az.hrportal.hrportalapi.constant.employee.Series;
import az.hrportal.hrportalapi.domain.employee.Address;
import az.hrportal.hrportalapi.domain.employee.Business;
import az.hrportal.hrportalapi.domain.employee.ContactInfo;
import az.hrportal.hrportalapi.domain.employee.Country;
import az.hrportal.hrportalapi.domain.employee.Education;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.employee.ForeignPassport;
import az.hrportal.hrportalapi.domain.employee.IDCard;
import az.hrportal.hrportalapi.dto.employee.request.AcademicRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.BusinessRequestDto;
import az.hrportal.hrportalapi.dto.employee.request.GeneralInfoRequestDto;
import az.hrportal.hrportalapi.dto.employee.response.GeneralInfoResponseDto;
import az.hrportal.hrportalapi.error.EntityNotFoundException;
import az.hrportal.hrportalapi.mapper.CertificateMapper;
import az.hrportal.hrportalapi.mapper.EmployeeMapper;
import az.hrportal.hrportalapi.mapper.FamilyMemberMapper;
import az.hrportal.hrportalapi.mapper.GovernmentAchievementMapper;
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

    @Transactional
    public void setPhotoName(Integer id, String fileName) {
        log.info("updatePhoto service started with id : {}, fileName : {}", id, fileName);
        Employee employee = employeeRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException(Employee.class));
        employee.setPhoto(fileName);
        employeeRepository.save(employee);
        log.info("updatePhoto service completed with id : {}, fileName : {}", id, fileName);
    }

    @Transactional
    @SneakyThrows
    public Integer saveGeneralInfo(GeneralInfoRequestDto generalInfoRequestDto) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Country country = countryRepository.findById(generalInfoRequestDto.getCitizenCountryId())
                .orElseThrow(() -> new EntityNotFoundException(Country.class));

        Employee employee = Employee.builder()
                .familyCondition(FamilyCondition.intToEnum(generalInfoRequestDto.getFamilyCondition()))
                .fullName(generalInfoRequestDto.getFullName())
                .birthDay(dateFormat.parse(generalInfoRequestDto.getBirthDay()))
                .birthPlace(generalInfoRequestDto.getBirthPlace())
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

        return saved.getId();
    }

    @Transactional
    @SneakyThrows
    public Integer updateGeneralInfo(Integer id, GeneralInfoRequestDto generalInfoRequestDto) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Employee.class));
        Country country = countryRepository.findById(generalInfoRequestDto.getCitizenCountryId())
                .orElseThrow(() -> new EntityNotFoundException(Country.class));

        employee.setFamilyCondition(FamilyCondition.intToEnum(generalInfoRequestDto.getFamilyCondition()));
        employee.setFullName(generalInfoRequestDto.getFullName());
        employee.setBirthDay(dateFormat.parse(generalInfoRequestDto.getBirthDay()));
        employee.setBirthPlace(generalInfoRequestDto.getBirthPlace());
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
        return saved.getId();
    }

    @Transactional
    @SneakyThrows
    public Integer updateBusiness(Integer id, BusinessRequestDto businessRequestDto) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Employee.class));

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
        return saved.getId();
    }

    @Transactional
    @SneakyThrows
    public Integer updateAcademic(Integer id, AcademicRequestDto academicRequestDto) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Employee.class));

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

        employee.setEducation(education);
        employee.setGovernmentAchievements(governmentAchievementMapper
                .tGovernmentAchievements(academicRequestDto.getGovernmentAchievements()));
        employee.setCertificates(certificateMapper.toCertificates(academicRequestDto.getCertificates()));
        employee.setKvota(Kvota.getKvota(academicRequestDto.getKvota()));
        employee.setMemberOfColleaguesAlliance(academicRequestDto.isMemberOfColleaguesAlliance());
        employee.setPrisoner(academicRequestDto.isPrisoner());
        //S.S Sehadetnamesi nomresi
        Employee saved = employeeRepository.save(employee);
        return saved.getId();
    }

    public GeneralInfoResponseDto getById(Integer id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Employee.class));

        return employeeMapper.toGeneralInfoRequestDto(employee);
    }

    @Transactional
    public Integer delete(Integer id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Employee.class));

        employeeRepository.delete(employee);
        return id;
    }

    /*@Transactional
    public Employee update(Integer id, EmployeeUpdateRequestDto employeeDto) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Employee.class));
        Address address = employee.getAddress();
        Business business = employee.getBusiness();
        Education education = employee.getEducation();
        ContactInfo contactInfo = employee.getContactInfo();
        ForeignPassport foreignPassport = employee.getForeignPassport();
        IDCard idCard = employee.getIdCard();

        if (employeeDto.getAddress() != null) {
            address = employeeMapper.toAddress(employeeDto.getAddress());
        }
        if (employeeDto.getBusiness() != null) {
            business = employeeMapper.toBusiness(employeeDto.getBusiness());
        }
        if (employeeDto.getEducation() != null) {
            education = employeeMapper.toEducation(employeeDto.getEducation());
        }
        if (employeeDto.getContactInfo() != null) {
            contactInfo = employeeMapper.toContactInfo(employeeDto.getContactInfo());
        }
        if (employeeDto.getForeignPassport() != null) {
            foreignPassport = employeeMapper.toForeignPassport(employeeDto.getForeignPassport());
        }
        if (employeeDto.getIdCard() != null) {
            idCard = employeeMapper.toIdCard(employeeDto.getIdCard());
        }

        employee.setAddress(address);
        employee.setBusiness(business);
        employee.setEducation(education);
        employee.setContactInfo(contactInfo);
        employee.setForeignPassport(foreignPassport);
        employee.setIdCard(idCard);
    }*/

}
