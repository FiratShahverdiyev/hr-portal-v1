package az.hrportal.hrportalapi.dto.employee.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class ContactInfoRequestDto {
    String homePhone;
    String mobilePhone1;
    String mobilePhone2;
    String businessPhone;
    String internalBusinessPhone;
    String ownMailAddress;
    String businessMailAddress;
}
