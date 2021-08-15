package az.hrportal.hrportalapi.domain;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "countries")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Country {
    @Id
    @SequenceGenerator(name = "countries_id_seq", allocationSize = 1, sequenceName = "countries_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "countries_id_seq")
    Integer id;
    @Column(name = "name")
    String name;
    @OneToMany(mappedBy = "citizenCountry")
    List<Employee> employees;
}
