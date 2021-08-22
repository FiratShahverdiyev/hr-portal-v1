package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.constant.position.EducationDegree;
import az.hrportal.hrportalapi.constant.position.GenderDemand;
import az.hrportal.hrportalapi.constant.position.Level;
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
import az.hrportal.hrportalapi.domain.position.Skill;
import az.hrportal.hrportalapi.domain.position.Speciality;
import az.hrportal.hrportalapi.domain.position.SubDepartment;
import az.hrportal.hrportalapi.domain.position.Vacancy;
import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.dto.position.request.GeneralInfoRequestDto;
import az.hrportal.hrportalapi.dto.position.request.KnowledgeRequestDto;
import az.hrportal.hrportalapi.dto.position.request.SkillRequestDto;
import az.hrportal.hrportalapi.dto.position.response.GeneralInfoResponseDto;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.mapper.position.KnowledgeMapper;
import az.hrportal.hrportalapi.mapper.position.PositionGeneralInfoMapper;
import az.hrportal.hrportalapi.repository.position.DepartmentRepository;
import az.hrportal.hrportalapi.repository.position.InstitutionRepository;
import az.hrportal.hrportalapi.repository.position.JobFamilyRepository;
import az.hrportal.hrportalapi.repository.position.PositionRepository;
import az.hrportal.hrportalapi.repository.position.SalaryRepository;
import az.hrportal.hrportalapi.repository.position.SkillRepository;
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
    private final SkillRepository skillRepository;
    private final KnowledgeMapper knowledgeMapper;
    private final PositionGeneralInfoMapper generalInfoMapper;

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
            subDepartment = saveSubDepartment(department, generalInfoRequestDto.getSubDepartmentName());
        } else {
            subDepartment = optionalSubDepartment.get();
        }

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

        List<Skill> skills = new ArrayList<>();
        for (SkillRequestDto skillRequestDto : generalInfoRequestDto.getSkills()) {
            Skill skill = skillRepository.findById(skillRequestDto.getSkillId()).orElseThrow(() ->
                    new EntityNotFoundException(Skill.class, skillRequestDto.getSkillId()));
            skill.setLevel(Level.intToEnum(skillRequestDto.getLevel()));
            skills.add(skill);
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
                .skills(skills)
                .fullNameAndPosition(generalInfoRequestDto.getFullNameAndPosition())
                .areaExperience(generalInfoRequestDto.getAreaExperience())
                .leaderExperience(generalInfoRequestDto.getLeaderExperience())
                .educationDegree(EducationDegree.intToEnum(generalInfoRequestDto.getEducationDegree()))
                .height(generalInfoRequestDto.getHeight())
                .healthy(generalInfoRequestDto.isHealthy())
                .militaryAchieve(generalInfoRequestDto.isMilitaryAchieve())
                .genderDemand(GenderDemand.intToEnum(generalInfoRequestDto.getGenderDemand()))
                .functionalities(generalInfoRequestDto.getFunctionalities())
                .workPlace(WorkPlace.intToEnum(generalInfoRequestDto.getWorkPlace()))
                .build();
        Position saved = positionRepository.save(position);

        log.info("********** saveGeneralInfo service completed with id : {} **********", saved.getId());
        return saved.getId();
    }

    @Transactional
    public Integer updateGeneralInfo(Integer id, GeneralInfoRequestDto generalInfoRequestDto) {
        log.info("updateGeneralInfo service started with {}", generalInfoRequestDto);

        Position position = positionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Position.class, id));

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
            subDepartment = saveSubDepartment(department, generalInfoRequestDto.getSubDepartmentName());
        } else {
            subDepartment = optionalSubDepartment.get();
        }

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

        List<Skill> skills = new ArrayList<>();
        for (SkillRequestDto skillRequestDto : generalInfoRequestDto.getSkills()) {
            Skill skill = skillRepository.findById(skillRequestDto.getSkillId()).orElseThrow(() ->
                    new EntityNotFoundException(Skill.class, skillRequestDto.getSkillId()));
            skill.setLevel(Level.intToEnum(skillRequestDto.getLevel()));
            skills.add(skill);
        }

        position.setInstitution(institution);
        position.setDepartment(department);
        position.setSubDepartment(subDepartment);
        position.setVacancy(vacancy);
        position.setJobFamily(jobFamily);
        position.setEducationSpeciality(speciality);
        position.setSalary(salary);
        position.setCount(generalInfoRequestDto.getVacancyCount());
        position.setWorkCalculateDegree(generalInfoRequestDto.getWorkCalculateDegree());
        position.setSubWorkCalculateDegree(SubWorkCalculateDegree
                .intToEnum(generalInfoRequestDto.getSubWorkCalculateDegree()));
        position.setWorkCondition(WorkCondition.intToEnum(generalInfoRequestDto.getWorkCondition()));
        position.setAdditionalSalary(generalInfoRequestDto.getAdditionalSalary());
        position.setWorkMode(WorkMode.intToEnum(generalInfoRequestDto.getWorkMode()));
        position.setVacancyCategory(VacancyCategory.intToEnum(generalInfoRequestDto.getVacancyCategory()));
        position.setSkills(skills);
        position.setFullNameAndPosition(generalInfoRequestDto.getFullNameAndPosition());
        position.setAreaExperience(generalInfoRequestDto.getAreaExperience());
        position.setLeaderExperience(generalInfoRequestDto.getLeaderExperience());
        position.setEducationDegree(EducationDegree.intToEnum(generalInfoRequestDto.getEducationDegree()));
        position.setHeight(generalInfoRequestDto.getHeight());
        position.setHealthy(generalInfoRequestDto.isHealthy());
        position.setMilitaryAchieve(generalInfoRequestDto.isMilitaryAchieve());
        position.setGenderDemand(GenderDemand.intToEnum(generalInfoRequestDto.getGenderDemand()));
        position.setFunctionalities(generalInfoRequestDto.getFunctionalities());
        position.setWorkPlace(WorkPlace.intToEnum(generalInfoRequestDto.getWorkPlace()));
        Position saved = positionRepository.save(position);

        log.info("********** updateGeneralInfo service completed with id : {} **********", saved.getId());
        return saved.getId();
    }

    @Transactional
    public Integer updateKnowledge(Integer id, KnowledgeRequestDto knowledgeRequestDto) {
        log.info("updateKnowledge service started with id : {}, {}", id, knowledgeRequestDto);
        Position position = positionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Position.class, id));
        position.setLegislationStatement(knowledgeMapper
                .toLegislationStatements(knowledgeRequestDto.getLegislationStatements()));
        position.setComputerKnowledge(knowledgeMapper
                .toComputerKnowledgeList(knowledgeRequestDto.getComputerKnowledge()));
        position.setLanguageKnowledge(knowledgeMapper
                .toLanguageKnowledgeList(knowledgeRequestDto.getLanguageKnowledge()));
        positionRepository.save(position);
        log.info("********** updateKnowledge service completed with id : {} **********", id);
        return id;
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
    protected SubDepartment saveSubDepartment(Department department, String name) {
        SubDepartment subDepartment = new SubDepartment();
        subDepartment.setName(name);
        subDepartment.setDepartment(department);
        return subDepartmentRepository.save(subDepartment);
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
        if (clazz.getSimpleName().equals(Salary.class.getSimpleName())) {
            Salary salary = new Salary();
            salary.setSalary((BigDecimal) value);
            return salaryRepository.save(salary);
        }
        throw new EntityNotFoundException(clazz, value);
    }

    public GeneralInfoResponseDto getGeneralInfoById(Integer id) {
        log.info("getGeneralInfoById service started with id : {}", id);
        Position position = positionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Position.class, id));
        log.info("********** getGeneralInfoById service completed with id : {} **********", id);
        return generalInfoMapper.toGeneralInfoResponseDto(position);
    }
}
