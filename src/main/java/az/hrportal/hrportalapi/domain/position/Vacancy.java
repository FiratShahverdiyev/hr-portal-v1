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
    Set<Position> positions;
    @CreationTimestamp
    @Column(name = "created_at")
    Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    Date updatedAt;
}
