package az.hrportal.hrportalapi.domain.employee;

import lombok.AccessLevel;
import lombok.Getter;
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
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "certificates")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Certificate {
    @Id
    @SequenceGenerator(name = "certificates_id_seq", allocationSize = 1, sequenceName = "certificates_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "certificates_id_seq")
    Integer id;
    @Column(name = "name")
    String name;
    @Column(name = "end_date")
    Date endDate;
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    Employee employee;
}
