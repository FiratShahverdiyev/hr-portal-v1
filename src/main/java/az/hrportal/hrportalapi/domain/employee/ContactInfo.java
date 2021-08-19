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
@Table(name = "contact_info")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContactInfo {
    @Id
    @Column(name = "employee_id")
    Integer id;
    @Column(name = "home_phone")
    String homePhone;
    @Column(name = "mobile_phone_1", nullable = false)
    String mobilePhone1;
    @Column(name = "mobile_phone_2")
    String mobilePhone2;
    @Column(name = "business_phone")
    String businessPhone;
    @Column(name = "internal_business_phone")
    String internalBusinessPhone;
    @Column(name = "own_mail_address")
    String ownMailAddress;
    @Column(name = "business_mail_address")
    String businessMailAddress;
    @OneToOne
    @MapsId
    @JoinColumn(name = "employee_id")
    Employee employee;
}
