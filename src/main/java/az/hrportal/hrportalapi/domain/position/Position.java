package az.hrportal.hrportalapi.domain.position;

import az.hrportal.hrportalapi.constant.employee.EducationDegree;
import az.hrportal.hrportalapi.constant.position.GenderDemand;
import az.hrportal.hrportalapi.constant.position.RequireFile;
import az.hrportal.hrportalapi.constant.position.VacancyCategory;
import az.hrportal.hrportalapi.constant.position.WorkMode;
import az.hrportal.hrportalapi.constant.position.WorkPlace;
import az.hrportal.hrportalapi.domain.embeddable.ComputerKnowledge;
import az.hrportal.hrportalapi.domain.embeddable.LanguageKnowledge;
import az.hrportal.hrportalapi.domain.embeddable.LegislationStatement;
import az.hrportal.hrportalapi.domain.embeddable.Skill;
import az.hrportal.hrportalapi.domain.employee.Employee;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "positions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Position {
    @Id
    @SequenceGenerator(name = "positions_id_seq", allocationSize = 1, sequenceName = "positions_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "positions_id_seq")
    Integer id;
    @ManyToOne(optional = false)
    Institution institution;
    @ManyToOne(optional = false)
    Department department;
    @ManyToOne(optional = false)
    SubDepartment subDepartment;
    @ManyToOne
    WorkCalculateDegree workCalculateDegree;
    @ManyToOne
    SubWorkCalculateDegree subWorkCalculateDegree;
    @ManyToOne(optional = false)
    Vacancy vacancy;
    @Column(name = "count", nullable = false)
    Integer count;
    @ManyToOne(optional = false)
    Salary salary;
    @ManyToOne(optional = false)
    WorkCondition workCondition;
    @Column(name = "additional_salary")
    Integer additionalSalary;
    @Column(name = "work_mode", nullable = false)
    @Enumerated(EnumType.STRING)
    WorkMode workMode;
    @Column(name = "vacancy_category", nullable = false)
    @Enumerated(EnumType.STRING)
    VacancyCategory vacancyCategory;
    @ManyToOne
    JobFamily jobFamily;
    @ElementCollection
    @CollectionTable(name = "position_skills", joinColumns = @JoinColumn(name = "position_id"))
    @Column(name = "skill")
    List<Skill> skills;
    @Column(name = "work_place", nullable = false)
    @Enumerated(EnumType.STRING)
    WorkPlace workPlace;
    @OneToMany(mappedBy = "position")
    List<Employee> employee;
    @Column(name = "area_experience")
    Integer areaExperience;
    @Column(name = "leader_experience")
    Integer leaderExperience;
    @Column(name = "education_degree")
    @Enumerated(EnumType.STRING)
    EducationDegree educationDegree;
    @ManyToOne
    Speciality educationSpeciality;
    @Column(name = "require_file")
    @Enumerated(EnumType.STRING)
    RequireFile requireFile;
    @Column(name = "length")
    Float length;
    @Column(name = "military_achievement")
    boolean isMilitaryAchieve;
    @Column(name = "gender_demand")
    @Enumerated(EnumType.STRING)
    GenderDemand genderDemand;
    @Column(name = "health")
    boolean isHealthy;
    @ElementCollection
    @CollectionTable(name = "position_computer_knowledge", joinColumns = @JoinColumn(name = "position_id"))
    @Column(name = "knowledge")
    List<ComputerKnowledge> computerKnowledge;
    @ElementCollection
    @CollectionTable(name = "position_language_knowledge", joinColumns = @JoinColumn(name = "position_id"))
    @Column(name = "knowledge")
    List<LanguageKnowledge> languageKnowledge;
    @ElementCollection
    @CollectionTable(name = "position_legislation_statement", joinColumns = @JoinColumn(name = "position_id"))
    @Column(name = "knowledge")
    List<LegislationStatement> legislationStatement;
}
