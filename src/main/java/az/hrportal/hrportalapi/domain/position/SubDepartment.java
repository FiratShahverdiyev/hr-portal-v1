package az.hrportal.hrportalapi.domain.position;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "sub_departments")
public class SubDepartment {
    @Id
    @SequenceGenerator(name = "sub_departments_id_seq", allocationSize = 1, sequenceName = "sub_departments_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_departments_id_seq")
    Integer id;
    @Column(name = "name")
    String name;
    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    Department department;
    @OneToMany(mappedBy = "subDepartment")
    List<ObeyDepartment> obeyDepartment;
    @OneToMany(mappedBy = "subDepartment")
    List<Position> positions;
}
