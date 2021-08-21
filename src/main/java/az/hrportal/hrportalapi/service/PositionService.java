package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.constant.position.EducationDegree;
import az.hrportal.hrportalapi.constant.position.GenderDemand;
import az.hrportal.hrportalapi.constant.position.SubWorkCalculateDegree;
import az.hrportal.hrportalapi.constant.position.VacancyCategory;
import az.hrportal.hrportalapi.constant.position.WorkCondition;
import az.hrportal.hrportalapi.constant.position.WorkMode;
import az.hrportal.hrportalapi.constant.position.WorkPlace;
import az.hrportal.hrportalapi.domain.position.Department;
import az.hrportal.hrportalapi.domain.position.Institution;
import az.hrportal.hrportalapi.domain.position.JobFamily;
import az.hrportal.hrportalapi.domain.position.Position;
import az.hrportal.hrportalapi.domain.position.Salary;
import az.hrportal.hrportalapi.domain.position.Speciality;
import az.hrportal.hrportalapi.domain.position.SubDepartment;
import az.hrportal.hrportalapi.domain.position.Vacancy;
import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.dto.position.request.GeneralInfoRequestDto;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.mapper.position.SkillMapper;
import az.hrportal.hrportalapi.repository.position.DepartmentRepository;
import az.hrportal.hrportalapi.repository.position.InstitutionRepository;
import az.hrportal.hrportalapi.repository.position.JobFamilyRepository;
import az.hrportal.hrportalapi.repository.position.PositionRepository;
import az.hrportal.hrportalapi.repository.position.SalaryRepository;
import az.hrportal.hrportalapi.repository.position.SpecialityRepository;
import az.hrportal.hrportalapi.repository.position.SubDepartmentRepository;
import az.hrportal.hrportalapi.repository.position.VacancyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;
    private final InstitutionRepository institutionRepository;
    private final DepartmentRepository departmentRepository;
    private final VacancyRepository vacancyRepository;
    private final JobFamilyRepository jobFamilyRepository;
    private final SpecialityRepository specialityRepository;
    private final SubDepartmentRepository subDepartmentRepository;
    private final SalaryRepository salaryRepository;
    private final SkillMapper skillMapper;

    @Transactional
    public Integer saveGeneralInfo(GeneralInfoRequestDto generalInfoRequestDto) {
        log.info("saveGeneralInfo service started with {}", generalInfoRequestDto);

        Optional<Institution> optionalInstitution = institutionRepository
                .findByName(generalInfoRequestDto.getInstitutionName());
        Institution institution;
        if (optionalInstitution.isEmpty()) {
            institution = (Institution) saveOther(generalInfoRequestDto.getInstitutionName(), Institution.class);
        } else {
            institution = optionalInstitution.get();
        }

        Optional<Department> optionalDepartment = departmentRepository
                .findByName(generalInfoRequestDto.getDepartmentName());
        Department department;
        if (optionalDepartment.isEmpty()) {
            department = (Department) saveOther(generalInfoRequestDto.getDepartmentName(), Department.class);
        } else {
            department = optionalDepartment.get();
        }

        Optional<SubDepartment> optionalSubDepartment = subDepartmentRepository
                .findByName(generalInfoRequestDto.getSubDepartmentName());
        SubDepartment subDepartment;
        if (optionalSubDepartment.isEmpty()) {
            subDepartment = (SubDepartment) saveOther(generalInfoRequestDto.getSubDepartmentName(),
                    SubDepartment.class);
        } else {
            subDepartment = optionalSubDepartment.get();
        }
        subDepartment.setDepartment(department);
        subDepartmentRepository.save(subDepartment);

        Optional<Vacancy> optionalVacancy = vacancyRepository
                .findByName(generalInfoRequestDto.getVacancyName());
        Vacancy vacancy;
        if (optionalVacancy.isEmpty()) {
            vacancy = (Vacancy) saveOther(generalInfoRequestDto.getVacancyName(), Vacancy.class);
        } else {
            vacancy = optionalVacancy.get();
        }

        Optional<JobFamily> optionalJobFamily = jobFamilyRepository
                .findByName(generalInfoRequestDto.getJobFamily());
        JobFamily jobFamily;
        if (optionalJobFamily.isEmpty()) {
            jobFamily = (JobFamily) saveOther(generalInfoRequestDto.getJobFamily(), JobFamily.class);
        } else {
            jobFamily = optionalJobFamily.get();
        }

        Optional<Speciality> optionalSpeciality = specialityRepository
                .findByName(generalInfoRequestDto.getEducationSpeciality());
        Speciality speciality;
        if (optionalSpeciality.isEmpty()) {
            speciality = (Speciality) saveOther(generalInfoRequestDto.getEducationSpeciality(), Speciality.class);
        } else {
            speciality = optionalSpeciality.get();
        }

        Optional<Salary> optionalSalary = salaryRepository
                .findBySalary(generalInfoRequestDto.getSalary());
        Salary salary;
        if (optionalSalary.isEmpty()) {
            salary = (Salary) saveOther(generalInfoRequestDto.getSalary(), Salary.class);
        } else {
            salary = optionalSalary.get();
        }

        Position position = Position.builder()
                .institution(institution)
                .department(department)
                .subDepartment(subDepartment)
                .vacancy(vacancy)
                .jobFamily(jobFamily)
                .educationSpeciality(speciality)
                .salary(salary)
                .count(generalInfoRequestDto.getVacancyCount())
                .workCalculateDegree(generalInfoRequestDto.getWorkCalculateDegree())
                .subWorkCalculateDegree(SubWorkCalculateDegree
                        .intToEnum(generalInfoRequestDto.getSubWorkCalculateDegree()))
                .workCondition(WorkCondition.intToEnum(generalInfoRequestDto.getWorkCondition()))
                .additionalSalary(generalInfoRequestDto.getAdditionalSalary())
                .workMode(WorkMode.intToEnum(generalInfoRequestDto.getWorkMode()))
                .vacancyCategory(VacancyCategory.intToEnum(generalInfoRequestDto.getVacancyCategory()))
                .skills(skillMapper.toSkills(generalInfoRequestDto.getSkills()))
                .fullNameAndPosition(generalInfoRequestDto.getFullNameAndPosition())
                .areaExperience(generalInfoRequestDto.getAreaExperience())
                .leaderExperience(generalInfoRequestDto.getLeaderExperience())
                .educationDegree(EducationDegree.intToEnum(generalInfoRequestDto.getEducationDegree()))
                .height(generalInfoRequestDto.getHeight())
                .healthy(generalInfoRequestDto.isHealthy())
                .militaryAchieve(generalInfoRequestDto.isMilitaryAchieve())
                .genderDemand(GenderDemand.intToEnum(generalInfoRequestDto.getGenderDemand()))
                .functionalities(generalInfoRequestDto.getFunctionalities())
                .build();
        Position saved = positionRepository.save(position);

        log.info("********** saveGeneralInfo service completed with id : {} **********", saved.getId());
        return saved.getId();
    }

    //TODO Delete on production
    public List<KeyValue<String, Integer>> getWorkAddress() {
        log.info("getWorkAddress service started");
        List<KeyValue<String, Integer>> response = new ArrayList<>();
        for (WorkPlace workPlaces : WorkPlace.values()) {
            KeyValue<String, Integer> keyValue = new KeyValue<>(workPlaces.toString(), workPlaces.getValue());
            response.add(keyValue);
        }
        log.info("********** getWorkAddress service completed **********");
        return response;
    }

    @Transactional
    protected Object saveOther(Object value, Class clazz) {
        if (clazz.getSimpleName().equals(Institution.class.getSimpleName())) {
            Institution institution = new Institution();
            institution.setName((String) value);
            return institutionRepository.save(institution);
        }
        if (clazz.getSimpleName().equals(Department.class.getSimpleName())) {
            Department department = new Department();
            department.setName((String) value);
            return departmentRepository.save(department);
        }
        if (clazz.getSimpleName().equals(Vacancy.class.getSimpleName())) {
            Vacancy vacancy = new Vacancy();
            vacancy.setName((String) value);
            return vacancyRepository.save(vacancy);
        }
        if (clazz.getSimpleName().equals(JobFamily.class.getSimpleName())) {
            JobFamily jobFamily = new JobFamily();
            jobFamily.setName((String) value);
            return jobFamilyRepository.save(jobFamily);
        }
        if (clazz.getSimpleName().equals(Speciality.class.getSimpleName())) {
            Speciality speciality = new Speciality();
            speciality.setName((String) value);
            return specialityRepository.save(speciality);
        }
        if (clazz.getSimpleName().equals(SubDepartment.class.getSimpleName())) {
            SubDepartment subDepartment = new SubDepartment();
            subDepartment.setName((String) value);
            return subDepartmentRepository.save(subDepartment);
        }
        if (clazz.getSimpleName().equals(Salary.class.getSimpleName())) {
            Salary salary = new Salary();
            salary.setSalary((BigDecimal) value);
            return salaryRepository.save(salary);
        }
        throw new EntityNotFoundException(clazz, value);
    }
}
