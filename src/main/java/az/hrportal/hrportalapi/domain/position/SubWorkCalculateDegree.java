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
@Table(name = "sub_work_calculate")
public class SubWorkCalculateDegree {
    @Id
    @SequenceGenerator(name = "sub_work_calculate_id_seq", allocationSize = 1,
            sequenceName = "sub_work_calculate_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_work_calculate_id_seq")
    Integer id;
    @Column(name = "name")
    String name;
    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false)
    WorkCalculateDegree workCalculateDegree;
    @OneToMany(mappedBy = "subWorkCalculateDegree")
    List<Position> positions;
}
