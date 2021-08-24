package az.hrportal.hrportalapi.service.position;

import az.hrportal.hrportalapi.constant.position.Level;
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
import az.hrportal.hrportalapi.dto.PaginationResponseDto;
import az.hrportal.hrportalapi.dto.position.request.GeneralInfoRequestDto;
import az.hrportal.hrportalapi.dto.position.request.KnowledgeRequestDto;
import az.hrportal.hrportalapi.dto.position.request.PositionFilterRequestDto;
import az.hrportal.hrportalapi.dto.position.request.SkillRequestDto;
import az.hrportal.hrportalapi.dto.position.response.GeneralInfoResponseDto;
import az.hrportal.hrportalapi.dto.position.response.KnowledgeResponseDto;
import az.hrportal.hrportalapi.dto.position.response.PositionResponseDto;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.mapper.position.PositionMapper;
import az.hrportal.hrportalapi.mapper.position.PositionResponseMapper;
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
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
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
    private final SkillRepository skillRepository;
    private final SalaryRepository salaryRepository;
    private final PositionResponseMapper positionResponseMapper;
    private final PositionMapper positionMapper;

    @Transactional
    public Integer saveGeneralInfo(GeneralInfoRequestDto generalInfoRequestDto) {
        log.info("saveGeneralInfo service started with {}", generalInfoRequestDto);
        Position position = new Position();
        updatePositionRelations(position, generalInfoRequestDto);
        positionMapper.updatePosition(position, generalInfoRequestDto);
        Position saved = positionRepository.save(position);
        log.info("********** saveGeneralInfo service completed with id : {} **********", saved.getId());
        return saved.getId();
    }

    @Transactional
    public Integer updateGeneralInfo(Integer id, GeneralInfoRequestDto generalInfoRequestDto) {
        log.info("updateGeneralInfo service started with {}", generalInfoRequestDto);
        Position position = positionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Position.class, id));
        updatePositionRelations(position, generalInfoRequestDto);
        positionMapper.updatePosition(position, generalInfoRequestDto);
        Position saved = positionRepository.save(position);
        log.info("********** updateGeneralInfo service completed with id : {} **********", saved.getId());
        return saved.getId();
    }

    @Transactional
    public Integer updateKnowledge(Integer id, KnowledgeRequestDto knowledgeRequestDto) {
        log.info("updateKnowledge service started with id : {}, {}", id, knowledgeRequestDto);
        Position position = positionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Position.class, id));
        positionMapper.updatePosition(position, knowledgeRequestDto);
        log.info("********** updateKnowledge service completed with id : {} **********", id);
        return id;
    }

    public PaginationResponseDto<List<PositionResponseDto>> getPaginationWithSearch(
            int page, int size, PositionFilterRequestDto filterRequestDto) {
        log.info("getPaginationWithSearch service started with {}", filterRequestDto);
        List<Position> positions = positionRepository.findAll();
        List<PositionResponseDto> responseDtoList = new ArrayList<>();
        positionFilter(positions, responseDtoList, filterRequestDto);
        PagedListHolder<PositionResponseDto> responseHolder = new PagedListHolder<>(responseDtoList);
        responseHolder.setPage(page);
        responseHolder.setPageSize(size);
        List<PositionResponseDto> response = responseHolder.getPageList();
        log.info("********** getPaginationWithSearch service completed **********");
        return new PaginationResponseDto<>(response, response.size(), responseDtoList.size());
    }

    public GeneralInfoResponseDto getGeneralInfoById(Integer id) {
        log.info("getGeneralInfoById service started with id : {}", id);
        Position position = positionRepository.findByIdWithSkillsAndFunctionalities(id).orElseThrow(() ->
                new EntityNotFoundException(Position.class, id));
        log.info("********** getGeneralInfoById service completed with id : {} **********", id);
        return positionResponseMapper.toGeneralInfoResponseDto(position);
    }

    public KnowledgeResponseDto getKnowledgeById(Integer id) {
        log.info("getKnowledgeById service started with id : {}", id);
        Position position = positionRepository.findByIdWithKnowledgeRelations(id).orElseThrow(() ->
                new EntityNotFoundException(Position.class, id));
        log.info("********** getKnowledgeById service completed with id : {} **********", id);
        return positionResponseMapper.toKnowledgeResponseDto(position);
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

    private void positionFilter(List<Position> filterData,
                                List<PositionResponseDto> response, PositionFilterRequestDto requestDto) {
        if (requestDto.getDepartment() == null && requestDto.getSubDepartment() == null &&
                requestDto.getVacancy() == null && requestDto.getVacancyCount() == null) {
            response.addAll(positionResponseMapper.toPositionResponseDtos(filterData));
            return;
        }
        for (Position position : filterData) {
            if (requestDto.getDepartment() != null &&
                    position.getDepartment().getName().equals(requestDto.getDepartment()))
                response.add(positionResponseMapper.toPositionResponseDto(position));

            if (requestDto.getSubDepartment() != null &&
                    position.getSubDepartment().getName().equals(requestDto.getSubDepartment()))
                response.add(positionResponseMapper.toPositionResponseDto(position));

            if (requestDto.getVacancy() != null &&
                    position.getVacancy().getName().equals(requestDto.getVacancy()))
                response.add(positionResponseMapper.toPositionResponseDto(position));

            if (requestDto.getVacancyCount() != null &&
                    position.getCount().equals(requestDto.getVacancyCount()))
                response.add(positionResponseMapper.toPositionResponseDto(position));
        }
    }

    private void updatePositionRelations(Position position, GeneralInfoRequestDto generalInfoRequestDto) {
        log.info("getRelations service started");
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
            skill.setLevel(Level.valueOf(skillRequestDto.getLevel()));
            skills.add(skill);
        }

        position.setInstitution(institution);
        position.setDepartment(department);
        position.setSubDepartment(subDepartment);
        position.setVacancy(vacancy);
        position.setJobFamily(jobFamily);
        position.setEducationSpeciality(speciality);
        position.setSalary(salary);
        position.setSkills(new HashSet<>(skills));
        log.info("********** getRelations service completed **********");
    }
}
