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
@Table(name = "departments")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Department {
    @Id
    @SequenceGenerator(name = "departments_id_seq", allocationSize = 1, sequenceName = "departments_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departments_id_seq")
    Integer id;
    @Column(name = "name", unique = true)
    String name;
    @OneToMany(mappedBy = "department")
    Set<SubDepartment> subDepartment;
    @OneToMany(mappedBy = "department")
    Set<Position> positions;
/*    @OneToMany(mappedBy = "department")
    Set<Operation> operations;*/
    @CreationTimestamp
    @Column(name = "created_at")
    Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    Date updatedAt;
}
