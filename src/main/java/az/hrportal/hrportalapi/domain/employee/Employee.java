package az.hrportal.hrportalapi.domain.employee;

import az.hrportal.hrportalapi.constant.EmployeeActivity;
import az.hrportal.hrportalapi.constant.employee.BloodGroup;
import az.hrportal.hrportalapi.constant.employee.DriverCategory;
import az.hrportal.hrportalapi.constant.employee.EducationType;
import az.hrportal.hrportalapi.constant.employee.FamilyCondition;
import az.hrportal.hrportalapi.constant.employee.Gender;
import az.hrportal.hrportalapi.constant.employee.MilitaryAchievement;
import az.hrportal.hrportalapi.constant.employee.Series;
import az.hrportal.hrportalapi.constant.position.WorkMode;
import az.hrportal.hrportalapi.domain.embeddable.Certificate;
import az.hrportal.hrportalapi.domain.embeddable.FamilyMember;
import az.hrportal.hrportalapi.domain.operation.Operation;
import az.hrportal.hrportalapi.domain.position.Position;
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
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "employees")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    @Column(name = "full_name")
    String fullName;
    @Column(name = "birthday")
    LocalDate birthday;
    @Column(name = "birthplace")
    String birthplace;
    @ManyToOne
    @JoinColumn(name = "citizen_country_id", referencedColumnName = "id")
    CitizenCountry citizenCountry;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    Gender gender;
    @Column(name = "blood_group")
    @Enumerated(EnumType.STRING)
    BloodGroup bloodGroup;
    @Column(name = "permission")
    String permission;
    @Column(name = "foreign_passport_series")
    @Enumerated(EnumType.STRING)
    Series foreignPassportSeries;
    @Column(name = "foreign_passport_number")
    String foreignPassportNumber;
    @Column(name = "foreign_passport_start_date")
    LocalDate foreignPassportStartDate;
    @Column(name = "foreign_passport_end_date")
    LocalDate foreignPassportEndDate;
    @Column(name = "id_card_series")
    @Enumerated(EnumType.STRING)
    Series IDCardSeries;
    @Column(name = "id_card_number")
    String IDCardNumber;
    @Column(name = "id_card_pin")
    String IDCardPin;
    @Column(name = "id_card_organization")
    String IDCardOrganization;
    @Column(name = "id_card_start_date")
    LocalDate IDCardStartDate;
    @Column(name = "id_card_end_date")
    LocalDate IDCardEndDate;
    @ManyToOne
    @JoinColumn(name = "address_country_id", referencedColumnName = "id")
    AddressCountry addressCountry;
    @ManyToOne
    @JoinColumn(name = "address_city_id", referencedColumnName = "id")
    AddressCity addressCity;
    @ManyToOne
    @JoinColumn(name = "address_district_id", referencedColumnName = "id")
    AddressDistrict addressDistrict;
    @Column(name = "address_village")
    String addressVillage;
    @Column(name = "address_street")
    String addressStreet;
    @Column(name = "address_block")
    String addressBlock;
    @Column(name = "address_apartment")
    String addressApartment;
    @Column(name = "address_home")
    String addressHome;
    @Column(name = "home_phone")
    String homePhone;
    @Column(name = "mobile_phone_1")
    String mobilePhone1;
    @Column(name = "mobile_phone_2")
    String mobilePhone2;
    @Column(name = "business_phone")
    String businessPhone;
    @Column(name = "internal_business_phone")
    String internalBusinessPhone;
    @Column(name = "own_mail_address")
    String ownMailAddress;
    @Column(name = "business_mail_address")
    String businessMailAddress;
    @Column(name = "family_member")
    @ElementCollection
    @CollectionTable(name = "employee_family_members", joinColumns = @JoinColumn(name = "employee_id"))
    Set<FamilyMember> familyMembers;
    @Column(name = "quota")
    @ElementCollection
    @CollectionTable(name = "employee_quotas", joinColumns = @JoinColumn(name = "employee_id"))
    Set<String> quotas;
    @Column(name = "business_company")
    String company;
    @Column(name = "business_section")
    String section;
    @Column(name = "business_sub_section")
    String subSection;
    @Column(name = "business_position")
    String businessPosition;
    @Column(name = "business_job_start_date")
    LocalDate jobStartDate;
    @Column(name = "business_job_end_date")
    LocalDate jobEndDate;
    @Column(name = "business_job_end_reason")
    String jobEndReason; //TODO hardan gelir ??
    @Column(name = "business_main_job")
    Boolean isMainJob;
    @Column(name = "academic_degree_start_date")
    LocalDate academicDegreeDate;
    @Column(name = "academic_degree_file_number")
    String academicDegreeNumber;
    @Column(name = "academic_degree_organization")
    String academicDegreeOrganization;
    @Column(name = "institution")
    String institution; //TODO hardan gelir ??
    @Column(name = "faculty")
    String faculty;
    @Column(name = "direction")
    String direction;
    @Column(name = "speciality")
    String speciality;
    @Column(name = "entrance_date")
    LocalDate entranceDate;
    @Column(name = "graduate_date")
    LocalDate graduateDate;
    @Column(name = "degree")
    String degree; //TODO Enum or keep  ??
    @Column(name = "graduate_file_number")
    String graduateFileNumber;
    @Column(name = "graduate_file_start_date")
    LocalDate graduateFileDate;
    @Column(name = "join_date")
    LocalDate joinDate;
    @Column(name = "education_type")
    @Enumerated(EnumType.STRING)
    EducationType educationType;
    @Column(name = "nostrifikasiya_number")
    String nostrifikasiyaNumber;
    @Column(name = "kvota")
    String kvota;
    @Column(name = "certificate")
    @ElementCollection
    @CollectionTable(name = "employee_certificates", joinColumns = @JoinColumn(name = "employee_id"))
    Set<Certificate> certificates;
    @ManyToMany
    @JoinTable(
            name = "employee_achievement",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "achievement_id")}
    )
    Set<GovernmentAchievement> governmentAchievements;
    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    Position position;
    @Column(name = "driver_card_category")
    @Enumerated(EnumType.STRING)
    DriverCategory driverCardCategory;
    @Column(name = "driver_card_end_date")
    LocalDate driverCardEndDate;
    @Column(name = "prisoner")
    Boolean prisoner;
    @Column(name = "colleagues_alliance")
    Boolean memberOfColleaguesAlliance;
    @Column(name = "activity")
    @Enumerated(EnumType.STRING)
    EmployeeActivity employeeActivity;
    @OneToMany(mappedBy = "employee")
    Set<Operation> operations;
    @Column(name = "work_permission_serial")
    String workPermissionSerial;
    @Column(name = "work_permission_number")
    String workPermissionNumber;
    @Column(name = "work_permission_period")
    Integer workPermissionPeriod;
    @Column(name = "salary")
    Float grossSalary;
    @Column(name = "net_income")
    Float netIncome;
    @Column(name = "start_work_permission_date")
    LocalDate startWorkPermissionDate;
    @Column(name = "expired_work_permission_date")
    LocalDate expiredWorkPermissionDate;
    @Column(name = "own_additional_salary")
    Float ownAdditionalSalary;
    @Column(name = "catch_amount")
    Float catchAmount;
    @Column(name = "catch_months")
    @ElementCollection
    Set<Integer> catchMonths;
    @Column(name = "event_from")
    LocalDate eventFrom;
    @Column(name = "event_to")
    LocalDate eventTo;
    @Column(name = "work_mode")
    @Enumerated(EnumType.STRING)
    WorkMode workMode;
    @OneToMany(mappedBy = "employee")
    Set<GrossSalary> grossSalaries;
    @Column(name = "gross_calculated")
    Boolean grossCalculated;
    Boolean higherEducation;
    @CreationTimestamp
    @Column(name = "created_at")
    Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    Date updatedAt;

    @PrePersist
    void setEmployeeActivity() {
        this.employeeActivity = EmployeeActivity.NONE;
    }
}
