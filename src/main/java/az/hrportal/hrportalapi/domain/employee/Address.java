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

@Entity
@Getter
@Setter
@Table(name = "address")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @Column(name = "employee_id")
    Integer id;
    @Column(name = "country", nullable = false)
    String country;
    @Column(name = "city", nullable = false)
    String city;
    @Column(name = "district", nullable = false)
    String district;
    @Column(name = "village", nullable = false)
    String village;
    @Column(name = "street", nullable = false)
    String street;
    @Column(name = "block", nullable = false)
    String block;
    @Column(name = "apartment", nullable = false)
    String apartment;
    @Column(name = "home", nullable = false)
    String home;
    @OneToOne
    @MapsId
    @JoinColumn(name = "employee_id")
    Employee employee;
}
