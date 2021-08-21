package az.hrportal.hrportalapi.dto.employee.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CertificateRequestDto {
    String name;
    String endDate;
}