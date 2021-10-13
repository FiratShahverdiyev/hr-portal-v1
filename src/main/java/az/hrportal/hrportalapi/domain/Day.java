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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "days")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Day {
    @Id
    @SequenceGenerator(name = "days_id_seq", allocationSize = 1, sequenceName = "days_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "days_id_seq")
    Integer id;

    @Column(name = "day", unique = true)
    LocalDate day;

    @Column(name = "job_day")
    boolean jobDay;

    @Column(name = "title")
    String title;
}
