package az.hrportal.hrportalapi.dto.employee.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressRequestDto {
    String country;
    String city;
    String district;
    String village;
    String street;
    String block;
    String apartment;
    String home;
}
