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
@Table(name = "job_families")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobFamily {
    @Id
    @SequenceGenerator(name = "job_families_id_seq", allocationSize = 1, sequenceName = "job_families_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_families_id_seq")
    Integer id;

    @Column(name = "name")
    String name;

    @OneToMany(mappedBy = "jobFamily")
    List<Position> positions;
}
