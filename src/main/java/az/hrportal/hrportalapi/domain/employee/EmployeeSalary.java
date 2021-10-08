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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "employees")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSalary {
    @Id
    @SequenceGenerator(name = "employees_id_seq", allocationSize = 1, sequenceName = "employees_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employees_id_seq")
    Integer id;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    Employee employee;
    @Column(name = "gross_salary")
    BigDecimal grossSalary;
    @Column(name = "dsmf")
    BigDecimal dsmf;
    @Column(name = "income_tax")
    BigDecimal incomeTax;
    @Column(name = "its")
    BigDecimal its;
    @Column(name = "trade_union")
    BigDecimal tradeUnion;
    @Column(name = "unemployment_insurance")
    BigDecimal unemploymentInsurance;
    @Column(name = "net_salary")
    BigDecimal netSalary;
    @Column(name = "active_days")
    Integer acctiveDays;
}
