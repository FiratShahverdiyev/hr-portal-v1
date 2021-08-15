package az.hrportal.hrportalapi.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

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
@Table(name = "family_members")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FamilyMember {
    @Id
    @SequenceGenerator(name = "family_members_id_seq", allocationSize = 1, sequenceName = "family_members_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "family_members_id_seq")
    Integer id;
    @Column(name = "type")
    String type; //TODO Enum
    @Column(name = "full_name")
    String fullName;
    @Column(name = "birthday")
    Date birthDay; // ?
    @Column(name = "birthplace")
    String birthPlace; //TODO Enum or entity
    @Column(name = "work_place")
    String workPlace;
    @Column(name = "position")
    String position;
    @Column(name = "address")
    String address;
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    Employee employee;
}
