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
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "skills")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Skill {
    @Id
    @SequenceGenerator(name = "skills_id_seq", allocationSize = 1, sequenceName = "skills_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skills_id_seq")
    Integer id;

    @Column(name = "name")
    String name;

    @Column(name = "level")
    int level;

    @ManyToMany(mappedBy = "skills")
    List<Position> positions;
}
