package az.hrportal.hrportalapi.domain.position;

import az.hrportal.hrportalapi.constant.Status;
import az.hrportal.hrportalapi.constant.position.EducationDegree;
import az.hrportal.hrportalapi.constant.position.GenderDemand;
import az.hrportal.hrportalapi.constant.position.RequireFile;
import az.hrportal.hrportalapi.constant.position.SubWorkCalculateDegree;
import az.hrportal.hrportalapi.constant.position.VacancyCategory;
import az.hrportal.hrportalapi.constant.position.WorkCondition;
import az.hrportal.hrportalapi.constant.position.WorkMode;
import az.hrportal.hrportalapi.constant.position.WorkPlace;
import az.hrportal.hrportalapi.domain.embeddable.ComputerKnowledge;
import az.hrportal.hrportalapi.domain.embeddable.LanguageKnowledge;
import az.hrportal.hrportalapi.domain.embeddable.LegislationStatement;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.operation.Operation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "positions")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    @Column(name = "work_calculate_degree")
    Integer workCalculateDegree;//grade
    @Column(name = "sub_work_calculate_degree")
    @Enumerated(EnumType.STRING)
    SubWorkCalculateDegree subWorkCalculateDegree;//subGrade
    @ManyToOne(optional = false)
    Vacancy vacancy;
    @Column(name = "leader_full_name_position")
    String fullNameAndPosition;
    @Column(name = "count", nullable = false)
    Integer count;
    @ManyToOne(optional = false)
    Salary salary;
    @Column(name = "work_condition")
    @Enumerated(EnumType.STRING)
    WorkCondition workCondition;
    @Column(name = "additional_salary")
    float additionalSalary;
    @Column(name = "work_mode", nullable = false)
    @Enumerated(EnumType.STRING)
    WorkMode workMode;
    @Column(name = "vacancy_category", nullable = false)
    @Enumerated(EnumType.STRING)
    VacancyCategory vacancyCategory;
    @ManyToOne
    JobFamily jobFamily;
    @ManyToMany
    @JoinTable(
            name = "position_skill",
            joinColumns = {@JoinColumn(name = "position_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")}
    )
    Set<Skill> skills;
    @Column(name = "work_place")
    @Enumerated(EnumType.STRING)
    WorkPlace workPlace;
    @OneToMany(mappedBy = "position")
    Set<Employee> employee;
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
    @Column(name = "height")
    float height;
    @Column(name = "military_achievement")
    boolean militaryAchieve;
    @Column(name = "gender_demand")
    @Enumerated(EnumType.STRING)
    GenderDemand genderDemand;
    @Column(name = "health")
    boolean healthy;
    @ElementCollection
    @CollectionTable(name = "position_computer_knowledge", joinColumns = @JoinColumn(name = "position_id"))
    @Column(name = "computer")
    Set<ComputerKnowledge> computerKnowledge;
    @ElementCollection
    @CollectionTable(name = "position_language_knowledge", joinColumns = @JoinColumn(name = "position_id"))
    @Column(name = "language")
    Set<LanguageKnowledge> languageKnowledge;
    @ElementCollection
    @CollectionTable(name = "position_legislation_statement", joinColumns = @JoinColumn(name = "position_id"))
    @Column(name = "legislation")
    Set<LegislationStatement> legislationStatements;
    @ElementCollection
    @CollectionTable(name = "position_functionalities", joinColumns = @JoinColumn(name = "position_id"))
    @Column(name = "functionality")
    Set<String> functionalities;
    @OneToMany(mappedBy = "position")
    Set<Operation> operations;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    Status status;
    @CreationTimestamp
    @Column(name = "created_at")
    Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    Date updatedAt;

    @PrePersist
    void setStatus() {
        status = Status.PENDING;
    }
}
