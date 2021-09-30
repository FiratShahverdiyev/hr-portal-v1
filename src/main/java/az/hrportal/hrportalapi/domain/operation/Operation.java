package az.hrportal.hrportalapi.domain.operation;

import az.hrportal.hrportalapi.constant.DocumentType;
import az.hrportal.hrportalapi.constant.Status;
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
import java.math.BigDecimal;
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
    /*@Column(name = "dismissal_date")
    LocalDate dismissalDate;
    @Column(name = "dismissal_reason")
    String dismissalReason;*/
    @Column(name = "compensation")
    BigDecimal compensation;
    @ElementCollection
    List<String> note;
    @Column(name = "join_date")
    LocalDate joinDate;
    @Column(name = "test_period")
    Integer testPeriod;
    @Column(name = "own_additional_salary")
    BigDecimal ownAdditionalSalary;
    @Column(name = "change_date")
    LocalDate changeDate;
    @Column(name = "new_own_additional_salary")
    BigDecimal newOwnAdditionalSalary;
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
