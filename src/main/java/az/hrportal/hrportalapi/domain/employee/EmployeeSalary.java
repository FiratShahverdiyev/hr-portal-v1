package az.hrportal.hrportalapi.domain.employee;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "employee_salaries")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSalary {
    @Id
    @SequenceGenerator(name = "employee_salaries_id_seq", allocationSize = 1, sequenceName = "employee_salaries_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_salaries_id_seq")
    Integer id;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    Employee employee;
    @Column(name = "gross_salary")
    Float grossSalary;
    @Column(name = "dsmf")
    Float dsmf;
    @Column(name = "income_tax")
    Float incomeTax;
    @Column(name = "its")
    Float its;
    @Column(name = "trade_union")
    Float tradeUnion;
    @Column(name = "unemployment_insurance")
    Float unemploymentInsurance;
    @Column(name = "net_salary")
    Float netSalary;
    @Column(name = "active_days")
    Integer activeDays;
    @Column(name = "salary_calculation_date")
    LocalDate salaryCalculationDate;
    @Column(name = "backup")
    Boolean backup;

    @PrePersist
    void setBackup() {
        this.backup = false;
    }
}
