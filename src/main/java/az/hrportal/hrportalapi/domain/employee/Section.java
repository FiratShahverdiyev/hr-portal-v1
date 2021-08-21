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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "sections")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Section {
    @Id
    @SequenceGenerator(name = "sections_id_seq", allocationSize = 1, sequenceName = "sections_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sections_id_seq")
    Integer id;
    @Column(name = "name")
    String name;
}