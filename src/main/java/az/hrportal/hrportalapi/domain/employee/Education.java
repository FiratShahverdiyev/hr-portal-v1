package az.hrportal.hrportalapi.domain.employee;

import az.hrportal.hrportalapi.constant.employee.EducationType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "educations")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Education {
    @Id
    @Column(name = "employee_id")
    Integer id;
    @Column(name = "academic_degree_start_date")
    String academicDegreeDate;
    @Column(name = "academic_degree_file_number")
    String academicDegreeNumber;
    @Column(name = "academic_degree_organization")
    String academicDegreeOrganization;
    @Column(name = "institution")
    String institution; //TODO hardan gelir ??
    @Column(name = "faculty")
    String faculty;
    @Column(name = "direction")
    String direction;
    @Column(name = "speciality")
    String speciality;
    @Column(name = "entrance_date")
    Date entranceDate;
    @Column(name = "graduate_date")
    Date graduateDate;
    @Column(name = "degree")
    String degree; //TODO Enum or keep  ??
    @Column(name = "graduate_file_number")
    String graduateFileNumber;
    @Column(name = "graduate_file_start_date")
    String graduateFileDate;
    @Column(name = "education_type")
    @Enumerated(EnumType.STRING)
    EducationType educationType;
    @OneToOne
    @MapsId
    @JoinColumn(name = "employee_id")
    Employee employee;
}
