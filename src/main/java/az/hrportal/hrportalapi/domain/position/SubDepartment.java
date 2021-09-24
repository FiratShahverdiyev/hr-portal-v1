package az.hrportal.hrportalapi.domain.position;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "sub_departments")
public class SubDepartment {
    @Id
    @SequenceGenerator(name = "sub_departments_id_seq", allocationSize = 1, sequenceName = "sub_departments_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_departments_id_seq")
    Integer id;
    @Column(name = "name", unique = true)
    String name;
    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    Department department;
    @OneToMany(mappedBy = "subDepartment")
    Set<ObeyDepartment> obeyDepartment;
    @OneToMany(mappedBy = "subDepartment")
    Set<Position> positions;
/*    @OneToMany(mappedBy = "subDepartment")
    Set<Operation> operations;*/
    @CreationTimestamp
    @Column(name = "created_at")
    Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    Date updatedAt;
}
