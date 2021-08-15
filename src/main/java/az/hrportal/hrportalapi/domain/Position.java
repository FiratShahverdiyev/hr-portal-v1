package az.hrportal.hrportalapi.domain;

import az.hrportal.hrportalapi.constant.EducationDegree;
import az.hrportal.hrportalapi.constant.GenderDemand;
import az.hrportal.hrportalapi.constant.RequireFile;
import az.hrportal.hrportalapi.constant.VacancyCategory;
import az.hrportal.hrportalapi.constant.WorkMode;
import az.hrportal.hrportalapi.constant.WorkPlace;
import az.hrportal.hrportalapi.dto.ComputerKnowledge;
import az.hrportal.hrportalapi.dto.LanguageKnowledge;
import az.hrportal.hrportalapi.dto.LegislationStatement;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
    //Struktur vahidi *
    //Struktur bolmesi *
    //Tabe struktur bolmesi *
    @ManyToOne(optional = false)
    Vacancy vacancy;
    @Column(name = "count", nullable = false)
    Integer count;
    //Əməyin ödənilməsi dərəcəsi
    //Əməyin ödənilməsi üzrə alt dərəcə
    @ManyToOne(optional = false)
    Salary salary;
    //Emek seraiti
    //Ştat üzrə əmək şəraitinə görə əlavə əmək haqqı
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
            name = "positions_skills",
            joinColumns = {@JoinColumn(name = "skill_id")},
            inverseJoinColumns = {@JoinColumn(name = "position_id")}
    )
    List<Skill> skills;
    @Column(name = "work_place", nullable = false)
    @Enumerated(EnumType.STRING)
    WorkPlace workPlace;
    //Struktur bölmələrin tabe olduqları kurator rəhbərlərin adı, soyadı, ata adı, vəzifəsi
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
