package az.hrportal.hrportalapi.domain.employee;

import lombok.AccessLevel;
import lombok.Getter;
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
public class Address {
    @Id
    @Column(name = "employee_id")
    Integer id;
    @Column(name = "address_country", nullable = false)
    String addressCountry;
    @Column(name = "address_city", nullable = false)
    String addressCity;
    @Column(name = "address_district", nullable = false)
    String addressDistrict;
    @Column(name = "address_village", nullable = false)
    String addressVillage;
    @Column(name = "address_street", nullable = false)
    String addressStreet;
    @Column(name = "address_block", nullable = false)
    String addressBlock;
    @Column(name = "address_apartment", nullable = false)
    String addressApartment;
    @Column(name = "address_home", nullable = false)
    String addressHome;
    @OneToOne
    @MapsId
    @JoinColumn(name = "employee_id")
    Employee employee;
}
