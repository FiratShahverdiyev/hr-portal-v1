package az.hrportal.hrportalapi.domain.position;

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
@Table(name = "specialities")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Speciality {
    @Id
    @SequenceGenerator(name = "specialities_id_seq", allocationSize = 1, sequenceName = "specialities_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "specialities_id_seq")
    Integer id;
    @Column(name = "name", unique = true)
    String name;
    @OneToMany(mappedBy = "educationSpeciality")
    Set<Position> positions;
    @CreationTimestamp
    @Column(name = "created_at")
    Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    Date updatedAt;
}
