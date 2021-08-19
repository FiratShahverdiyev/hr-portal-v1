package az.hrportal.hrportalapi.domain.employee;

import az.hrportal.hrportalapi.constant.employee.BloodGroup;
import az.hrportal.hrportalapi.constant.employee.DriverCategory;
import az.hrportal.hrportalapi.constant.employee.FamilyCondition;
import az.hrportal.hrportalapi.constant.employee.Gender;
import az.hrportal.hrportalapi.constant.employee.MilitaryAchievement;
import az.hrportal.hrportalapi.domain.position.Position;
import az.hrportal.hrportalapi.dto.Certificate;
import az.hrportal.hrportalapi.dto.FamilyMember;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "employees")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {
    @Id
    @SequenceGenerator(name = "employees_id_seq", allocationSize = 1, sequenceName = "employees_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employees_id_seq")
    Integer id;

    @Column(name = "photo")
    String photo;

    @Column(name = "family_condition")
    @Enumerated(EnumType.STRING)
    FamilyCondition familyCondition;
    @Column(name = "military_achievement")
    @Enumerated(EnumType.STRING)
    MilitaryAchievement militaryAchievement;

    @Column(name = "full_name", nullable = false)
    String fullName;
    @Column(name = "birthday", nullable = false)
    Date birthDay;
    @Column(name = "birthplace", nullable = false)
    String birthPlace;
    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    Country citizenCountry;
    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    Gender gender;
    @Column(name = "blood_group")
    @Enumerated(EnumType.STRING)
    BloodGroup bloodGroup;

    @Column(name = "permission")
    String permission;
    //Vesiqe veya muveqqeti yasamaq icazesi nedir ?
    @OneToOne(mappedBy = "employee")
    ForeignPassport foreignPassport;

    @OneToOne(mappedBy = "employee")
    Address address;

    @OneToOne(mappedBy = "employee")
    @PrimaryKeyJoinColumn
    ContactInfo contactInfo;

    //Family
    @Column(name = "family_member")
    @ElementCollection
    @CollectionTable(name = "employee_family_members", joinColumns = @JoinColumn(name = "employee_id"))
    List<FamilyMember> familyMembers;

    @Column(name = "quota")
    @ElementCollection
    @CollectionTable(name = "employee_quotas", joinColumns = @JoinColumn(name = "employee_id"))
    List<String> quotas;

    @OneToOne(mappedBy = "employee")
    @PrimaryKeyJoinColumn
    Business business;

    @OneToOne(mappedBy = "employee")
    @PrimaryKeyJoinColumn
    Education education;
    //Nostrifikasiya sehadetnamesinin nomresi

    //Achievements
    @Column(name = "certificate")
    @ElementCollection
    @CollectionTable(name = "employee_certificates", joinColumns = @JoinColumn(name = "employee_id"))
    List<Certificate> certificates;
    @OneToMany(mappedBy = "employee")
    List<GovernmentAchievement> governmentAchievements;

    @OneToMany(mappedBy = "employee")
    List<Position> positions;

    @Column(name = "driver_card_category")
    @Enumerated(EnumType.STRING)
    DriverCategory driverCardCategory;
    @Column(name = "driver_card_end_date")
    Date driverCardEndDate;

    @Column(name = "prisoner")
    boolean isPrisoner;
    @Column(name = "colleagues_alliance")
    boolean isMemberOfColleaguesAlliance;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
