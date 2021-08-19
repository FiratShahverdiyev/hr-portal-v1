package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.constant.employee.BloodGroup;
import az.hrportal.hrportalapi.constant.employee.EducationType;
import az.hrportal.hrportalapi.constant.employee.FamilyCondition;
import az.hrportal.hrportalapi.constant.employee.Gender;
import az.hrportal.hrportalapi.constant.employee.Series;
import az.hrportal.hrportalapi.domain.employee.Address;
import az.hrportal.hrportalapi.domain.employee.Business;
import az.hrportal.hrportalapi.domain.employee.ContactInfo;
import az.hrportal.hrportalapi.domain.employee.Country;
import az.hrportal.hrportalapi.domain.employee.Education;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.employee.ForeignPassport;
import az.hrportal.hrportalapi.domain.employee.IDCard;
import az.hrportal.hrportalapi.dto.AcademicRequestDto;
import az.hrportal.hrportalapi.dto.BusinessRequestDto;
import az.hrportal.hrportalapi.dto.GeneralInfoRequestDto;
import az.hrportal.hrportalapi.error.EntityNotFoundException;
import az.hrportal.hrportalapi.mapper.EmployeeMapper;
import az.hrportal.hrportalapi.repository.AddressRepository;
import az.hrportal.hrportalapi.repository.BusinessRepository;
import az.hrportal.hrportalapi.repository.ContactInfoRepository;
import az.hrportal.hrportalapi.repository.CountryRepository;
import az.hrportal.hrportalapi.repository.EducationRepository;
import az.hrportal.hrportalapi.repository.EmployeeRepository;
import az.hrportal.hrportalapi.repository.ForeignPassportRepository;
import az.hrportal.hrportalapi.repository.IDCardRepository;
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
    private final EmployeeMapper employeeMapper;
    private final CountryRepository countryRepository;
    private final AddressRepository addressRepository;
    private final ContactInfoRepository contactInfoRepository;
    private final ForeignPassportRepository foreignPassportRepository;
    private final IDCardRepository idCardRepository;
    private final BusinessRepository businessRepository;
    private final EducationRepository educationRepository;

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
                .familyMembers(employeeMapper.toFamilyMembers(generalInfoRequestDto.getFamilyMembers()))
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
                .build();
        educationRepository.save(education);

        employee.setEducation(education);
        Employee saved = employeeRepository.save(employee);
        return saved.getId();
    }
}
