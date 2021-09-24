package az.hrportal.hrportalapi.domain.position;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "obey_departments")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ObeyDepartment {
    @Id
    @SequenceGenerator(name = "obey_departments_id_seq", allocationSize = 1, sequenceName = "obey_departments_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "obey_departments_id_seq")
    Integer id;
    @Column(name = "name", unique = true)
    String name;
    @ManyToOne
    @JoinColumn(name = "sub_department_id", referencedColumnName = "id", nullable = false)
    SubDepartment subDepartment;
    @CreationTimestamp
    @Column(name = "created_at")
    Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    Date updatedAt;
}
