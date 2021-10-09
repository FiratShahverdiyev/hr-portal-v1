package az.hrportal.hrportalapi.domain.operation;

import az.hrportal.hrportalapi.constant.DisciplineType;
import az.hrportal.hrportalapi.constant.DocumentType;
import az.hrportal.hrportalapi.constant.Status;
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
    LocalDate dismissalDate;
    @Column(name = "dismissal_reason")
    String dismissalReason;
    @Column(name = "compensation")
    Float compensation;
    @ElementCollection
    List<String> notes;
    @Column(name = "join_date")
    LocalDate joinDate;
    @Column(name = "test_period")
    Integer testPeriod;
    @Column(name = "own_additional_salary")
    Float ownAdditionalSalary;
    @Column(name = "change_date")
    LocalDate changeDate;
    @Column(name = "new_own_additional_salary")
    Float newOwnAdditionalSalary;
    @Column(name = "new_additional_salary")
    Float newAdditionalSalary;
    @Column(name = "new_salary")
    Float newSalary;
    @Column(name = "financial_help")
    Float financialHelp;
    @Column(name = "achievement")
    Float achievement;
    @Column(name = "change_period")
    Integer changePeriod;
    @Column(name = "work_mode")
    @Enumerated(EnumType.STRING)
    WorkMode workMode;
    @Column(name = "event_from")
    LocalDate eventFrom;
    @Column(name = "event_to")
    LocalDate eventTo;
    @Column(name = "event_name")
    String eventName;
    @Column(name = "day_in_event")
    Integer dayInEvent;
    @Column(name = "discipline_type")
    @Enumerated(EnumType.STRING)
    DisciplineType disciplineType;
    @Column(name = "presentation_owner_name")
    String presentationOwnerName;
    @Column(name = "presentation_owner_department")
    String presentationOwnerDepartment;
    @Column(name = "presentation_owner_position")
    String presentationOwnerPosition;
    @ElementCollection
    List<Integer> employeeIds;
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
