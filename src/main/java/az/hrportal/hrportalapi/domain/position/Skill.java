package az.hrportal.hrportalapi.domain.position;

import az.hrportal.hrportalapi.constant.position.Level;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
import javax.persistence.UniqueConstraint;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "skills", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "level"})})
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
    @CreationTimestamp
    @Column(name = "created_at")
    Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    Date updatedAt;
}
