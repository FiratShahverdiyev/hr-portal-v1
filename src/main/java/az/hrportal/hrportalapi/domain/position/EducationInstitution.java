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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "education_institutions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EducationInstitution {
    @Id
    @SequenceGenerator(name = "education_institutions_id_seq",
            allocationSize = 1, sequenceName = "education_institutions_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "education_institutions_id_seq")
    Integer id;
    @Column(name = "name", unique = true)
    String name;
/*    @OneToMany(mappedBy = "institution")
//    @JoinColumn(name = "position_id", referencedColumnName = "id", nullable = false)
    Set<Position> positions;*/
    @CreationTimestamp
    @Column(name = "created_at")
    Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    Date updatedAt;
}
