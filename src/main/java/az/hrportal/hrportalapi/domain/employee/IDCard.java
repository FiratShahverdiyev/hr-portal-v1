package az.hrportal.hrportalapi.domain.employee;

import az.hrportal.hrportalapi.constant.employee.Series;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "id_card")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IDCard {
    @Id
    @Column(name = "employee_id")
    Integer id;
    @Column(name = "series", nullable = false)
    @Enumerated(EnumType.STRING)
    Series series;
    @Column(name = "number", nullable = false)
    String number;
    @Column(name = "pin", nullable = false)
    String pin;
    @Column(name = "organization", nullable = false)
    String organization;
    @Column(name = "start_date", nullable = false)
    Date startDate;
    @Column(name = "end_date", nullable = false)
    Date endDate;
    @OneToOne
    @MapsId
    @JoinColumn(name = "employee_id")
    Employee employee;
}
