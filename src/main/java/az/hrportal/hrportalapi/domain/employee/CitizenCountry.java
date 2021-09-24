package az.hrportal.hrportalapi.domain.employee;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "citizen_countries")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CitizenCountry {
    @Id
    @SequenceGenerator(name = "citizen_countries_id_seq", allocationSize = 1, sequenceName = "citizen_countries_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "citizen_countries_id_seq")
    Integer id;
    @Column(name = "name", unique = true)
    String name;
    @OneToMany(mappedBy = "citizenCountry")
    Set<Employee> employees;
    @CreationTimestamp
    @Column(name = "created_at")
    Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    Date updatedAt;
}
