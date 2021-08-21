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
import java.util.List;

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
    @Column(name = "name")
    String name;
    @OneToMany(mappedBy = "department")
    List<SubDepartment> subDepartment;
    @OneToMany(mappedBy = "department")
    List<Position> positions;
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
