package az.hrportal.hrportalapi.domain.position;

import az.hrportal.hrportalapi.constant.position.Level;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @SequenceGenerator(name = "skills_id_seq", allocationSize = 1, sequenceName = "skills_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skills_id_seq")
    Integer id;
    @Column(name = "name")
    String name;
    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    Level level;
    @ManyToMany(mappedBy = "skills")
    Set<Position> positions;

    @PrePersist
    private void setLevel() {
        level = Level.NONE;
    }
}
