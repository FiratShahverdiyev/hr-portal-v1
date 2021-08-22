
package az.hrportal.hrportalapi.dto.employee.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class AddressResponseDto {
    String country;
    String city;
    String district;
    String village;
    String street;
    String block;
    String apartment;
    String home;
}
