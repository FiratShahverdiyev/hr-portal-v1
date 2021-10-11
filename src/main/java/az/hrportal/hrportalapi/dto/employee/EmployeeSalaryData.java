package az.hrportal.hrportalapi.dto.employee;

import az.hrportal.hrportalapi.domain.position.Position;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class EmployeeSalaryData {
    Integer id;
    String fullName;
    Position position;
    float salary;
    Set<String> quotas;
}
