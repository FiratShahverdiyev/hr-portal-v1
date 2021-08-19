package az.hrportal.hrportalapi.domain.employee;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "business")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Business {
    @Id
    @Column(name = "employee_id")
    Integer id;
    @Column(name = "company")
    String company;
    @Column(name = "section")
    String section;
    @Column(name = "sub_section")
    String subSection;
    @Column(name = "position")
    String position;
    @Column(name = "job_start_date")
    Date jobStartDate;
    @Column(name = "job_end_date")
    Date jobEndDate;
    @Column(name = "job_end_reason")
    String jobEndReason; //TODO hardan gelir ??
    @Column(name = "main_job")
    boolean isMainJob;
    @OneToOne
    @MapsId
    @JoinColumn(name = "employee_id")
    Employee employee;
}
