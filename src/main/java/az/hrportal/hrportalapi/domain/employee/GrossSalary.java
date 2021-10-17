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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "gross_salary")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrossSalary {

    @Id
    @SequenceGenerator(name = "gross_salary_id_seq", allocationSize = 1, sequenceName = "gross_salary_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gross_salary_id_seq")
    Integer id;


    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    Employee employee;

    LocalDate date;
    @Column(name = "amount")
    float amount;
}
