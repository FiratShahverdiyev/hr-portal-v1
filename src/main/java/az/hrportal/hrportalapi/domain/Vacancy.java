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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "vacancies")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vacancy {
    @Id
    @SequenceGenerator(name = "vacancies_id_seq", allocationSize = 1, sequenceName = "vacancies_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacancies_id_seq")
    Integer id;

    @Column(name = "name")
    String name;

    @OneToMany(mappedBy = "vacancy")
    List<Position> positions;
}
