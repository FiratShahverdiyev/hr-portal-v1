package az.hrportal.hrportalapi.domain.operation;

import az.hrportal.hrportalapi.constant.DisciplineType;
import az.hrportal.hrportalapi.constant.DocumentType;
import az.hrportal.hrportalapi.constant.Status;
import az.hrportal.hrportalapi.constant.employee.Series;
import az.hrportal.hrportalapi.constant.position.WorkMode;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.position.Position;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "operations")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    @Id
    @SequenceGenerator(name = "operations_id_seq", allocationSize = 1, sequenceName = "operations_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operations_id_seq")
    Integer id;
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    Employee employee;
    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    Position position;
    @Column(name = "main")
    String mainOfOrder;
    @Column(name = "title_department")
    String titleDepartment;
    @Column(name = "title_full_name")
    String titleFullName;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    DocumentType documentType;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    Status status;
    @Column(name = "dismissal_date")
    LocalDate dismissalDate; //Xitam edilme tarixi
    @Column(name = "dismissal_reason")
    String dismissalReason; //Xitam sebebi
    @Column(name = "compensation")
    float compensation; //Xitamdan sonra isciye verilecek kompensasiya
    @ElementCollection
    List<String> notes;
    @Column(name = "join_date")
    LocalDate joinDate; //Ise qebul olunma tarixi
    @Column(name = "test_period")
    Integer testPeriod; //Iscinin sinaq muddeti
    @Column(name = "own_additional_salary")
    float ownAdditionalSalary; //Ishe qebulda isciye verilecek diger ferdi elave
    @Column(name = "change_date")
    LocalDate changeDate; //Deyisiklik tarixi. Sual_1: Deyisiklik tarixi ne demekdir ?
    @Column(name = "new_own_additional_salary")
    float newOwnAdditionalSalary; //Iscinin yeni ferdi elavesi
    @Column(name = "new_additional_salary")
    float newAdditionalSalary; //Sual_2: pdfChangeEmployeeSalary duzgun yazilib ?
    @Column(name = "new_salary")
    float newSalary; //Sual_3: pdfAdditionalSalary duzgun yazilib ?
    @Column(name = "financial_help")
    float financialHelp; //Maddi yardim
    @Column(name = "achievement_amount")
    float achievementAmount; //Mukafatin meblegi
    @Column(name = "catch_amount")
    float catchAmount; //Tutulma meblegi
    @Column(name = "new_term")
    Integer newTerm; //Sual_4 Keçirildiyi müddət nedir ?
    @Column(name = "work_mode")
    @Enumerated(EnumType.STRING)
    WorkMode workMode; //Iscinin kecirildiyi is rejimi
    @Column(name = "event_from")
    LocalDate eventFrom; //
    @Column(name = "event_to")
    LocalDate eventTo; //
    @Column(name = "event_name")
    String eventName; //
    @Column(name = "day_in_event")
    Integer dayInEvent; //
    @Column(name = "discipline_type")
    @Enumerated(EnumType.STRING)
    DisciplineType disciplineType; //İntizam tənbehinin növü
    @Column(name = "presentation_owner_name")
    String presentationOwnerName;
    @Column(name = "presentation_owner_department")
    String presentationOwnerDepartment;
    @Column(name = "presentation_owner_position")
    String presentationOwnerPosition;
    @Column(name = "call_back_reason")
    String callBackReason;
    @Column(name = "call_back_date")
    LocalDate callBackDate;
    @Column(name = "catch_months")
    @ElementCollection
    Set<Integer> catchMonths;
    @Column(name = "year")
    Integer year;
    @ElementCollection
    List<Integer> employeeIds;
    @Column(name = "assignment_date")
    LocalDate assignmentTerm; // Hevale muddeti
    @Column(name = "alternate_worker_salary")
    float alternateWorkerSalary;
    @Column(name = "amount")
    float amount;
    @Column(name = "business_trip_location")
    String businessTripLocation;
    @Column(name = "event_to_business_trip_date")
    LocalDate eventToBusinessTripDate;
    @Column(name = "event_from_business_trip_date")
    LocalDate eventFromBusinessTripDate;
    @Column(name = "business_trip_term")
    Integer businessTripTerm;
    //?
    @Column(name = "non_working_day")
    LocalDate nonWorkDay;
    @Column(name = "given_non_work_day")
    LocalDate givenNonWorkDay;
    @Column(name = "serial_number")
    String serialNumber;
    @Column(name = "series")
    @Enumerated(EnumType.STRING)
    Series series;
    @Column(name = "event_from_2")
    LocalDate eventFrom2; //Sual_5: Əmək məzuniyyətinin uzadılması barədə
    @Column(name = "event_to_2")
    LocalDate eventTo2; //Sual_5: Əmək məzuniyyətinin uzadılması barədə
    @Column(name = "day_in_event_2")
    Integer dayInEvent2; //Sual_5: Əmək məzuniyyətinin uzadılması barədə
    @Column(name = "reason")
    String reason;
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
