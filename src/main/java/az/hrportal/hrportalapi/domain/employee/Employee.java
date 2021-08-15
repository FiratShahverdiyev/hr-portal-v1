package az.hrportal.hrportalapi.domain.employee;

import az.hrportal.hrportalapi.constant.employee.BloodGroup;
import az.hrportal.hrportalapi.constant.employee.DriverCategory;
import az.hrportal.hrportalapi.constant.employee.EducationType;
import az.hrportal.hrportalapi.constant.employee.FamilyCondition;
import az.hrportal.hrportalapi.constant.employee.Gender;
import az.hrportal.hrportalapi.constant.employee.MilitaryAchievement;
import az.hrportal.hrportalapi.constant.employee.Series;
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
    String photoUrl;
    @Column(name = "id_card_series", nullable = false)
    @Enumerated(EnumType.STRING)
    Series idCardSeries;
    @Column(name = "id_card_number", nullable = false)
    String idCardNumber;
    @Column(name = "id_card_pin", nullable = false)
    String idCardPin;
    @Column(name = "id_card_organization", nullable = false)
    String idCardOrganization;
    @Column(name = "id_card_start_date", nullable = false)
    Date idCardStartDate;
    @Column(name = "id_card_end_date", nullable = false)
    Date idCardEndDate;
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

    //Foreign card
    @Column(name = "foreign_passport_series")
    @Enumerated(EnumType.STRING)
    Series foreignPassportSeries;
    @Column(name = "foreign_passport_number", nullable = false)
    String foreignPassportNumber;
    @Column(name = "foreign_passport_start_date", nullable = false)
    Date foreignPassportStartDate;
    @Column(name = "foreign_passport_end_date", nullable = false)
    Date foreignPassportEndDate;

    //Address
    @Column(name = "address_country", nullable = false)
    String addressCountry;
    @Column(name = "address_city", nullable = false)
    String addressCity;
    @Column(name = "address_district", nullable = false)
    String addressDistrict;
    @Column(name = "address_village", nullable = false)
    String addressVillage;
    @Column(name = "address_street", nullable = false)
    String addressStreet;
    @Column(name = "address_block", nullable = false)
    String addressBlock;
    @Column(name = "address_apartment", nullable = false)
    String addressApartment;
    @Column(name = "address_home", nullable = false)
    String addressHome;


    //Contact info
    @Column(name = "home_phone")
    String homePhone;
    @Column(name = "mobile_phone_1", nullable = false)
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

    //Family
    @OneToMany(mappedBy = "employee")
    List<FamilyMember> familyMembers;

    @Column(name = "quota")
    @ElementCollection
    @CollectionTable(name = "employee_quotas", joinColumns = @JoinColumn(name = "employee_id"))
    List<String> quotas;

    //Business
    @Column(name = "company")
    String company;
    @Column(name = "section")
    String section;
    @Column(name = "sub_section")
    String subSection;
    @Column(name = "position")
    String position;
    @Column(name = "job_start_date")
    Date jobStartDate;
    @Column(name = "job_end_date")
    Date jobEndDate;
    //Isden azad olma maddes
    @Column(name = "job_end_reason")
    String jobEndReason; //TODO hardan gelir ??
    @Column(name = "main_job")
    boolean isMainJob;

    //Education
    @Column(name = "academic_degree_start_date")
    String academicDegreeDate;
    @Column(name = "academic_degree_file_number")
    String academicDegreeNumber;
    @Column(name = "academic_degree_organization")
    String academicDegreeOrganization;
    @Column(name = "educational_institution")
    String educationalInstitution; //TODO hardan gelir ??
    @Column(name = "faculty")
    String faculty;
    @Column(name = "direction")
    String direction;
    @Column(name = "speciality")
    String speciality;
    @Column(name = "entrance_date")
    Date entranceDate;
    @Column(name = "graduate_date")
    Date graduateDate;
    @Column(name = "degree")
    String degree; //TODO Enum or keep  ??
    @Column(name = "graduate_file_number")
    String graduateFileNumber;
    @Column(name = "graduate_file_start_date")
    String graduateFileDate;
    @Column(name = "education_type")
    @Enumerated(EnumType.STRING)
    EducationType educationType;

    //Nostrifikasiya sehadetnamesinin nomresi

    //Achievements
    @OneToMany(mappedBy = "employee")
    List<Certificate> certificates;
    @OneToMany(mappedBy = "employee")
    List<GovernmentAchievement> governmentAchievements;

    @Column(name = "driver_card_category")
    @Enumerated(EnumType.STRING)
    DriverCategory driverCardCategory;
    @Column(name = "driver_card_end_date")
    Date driverCardEndDate;

    @Column(name = "prisoner")
    boolean isPrisoner;
    @Column(name = "colleagues_alliance")
    boolean isMemberOfColleaguesAlliance;
}
