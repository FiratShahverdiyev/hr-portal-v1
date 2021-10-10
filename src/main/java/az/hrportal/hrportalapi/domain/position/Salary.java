package az.hrportal.hrportalapi.domain.position;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "salaries")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Salary {
    @Id
    @SequenceGenerator(name = "salaries_id_seq", allocationSize = 1, sequenceName = "salaries_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salaries_id_seq")
    Integer id;
    @Column(name = "salary", unique = true)
    float salary;
    @OneToMany(mappedBy = "salary")
    Set<Position> positions;
    @CreationTimestamp
    @Column(name = "created_at")
    Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    Date updatedAt;
}
