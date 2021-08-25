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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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
    @Column(name = "name")
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
