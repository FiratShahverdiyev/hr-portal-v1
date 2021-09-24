package az.hrportal.hrportalapi.domain.operation;

import az.hrportal.hrportalapi.constant.DocumentStatus;
import az.hrportal.hrportalapi.constant.DocumentType;
import az.hrportal.hrportalapi.domain.employee.Employee;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
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
/*    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    Position position;*/
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    DocumentType documentType;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    DocumentStatus documentStatus;
    @Column(name = "dismissal_date")
    LocalDate dismissalDate;
    @Column(name = "dismissal_reason")
    String dismissalReason;
    @Column(name = "compensation")
    BigDecimal compensation;
    @ElementCollection
    List<String> note;
    @Column(name = "salary")
    BigDecimal salary;
    @Column(name = "join_date")
    LocalDate joinDate;
    @Column(name = "test_period")
    Integer testPeriod;
    @Column(name = "own_additional_salary")
    BigDecimal ownAdditionalSalary;
    @Column(name = "change_date")
    LocalDate changeDate;
    @Column(name = "department")
    String department;
    @Column(name = "position")
    String position;
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
        documentStatus = DocumentStatus.PENDING;
    }
}
