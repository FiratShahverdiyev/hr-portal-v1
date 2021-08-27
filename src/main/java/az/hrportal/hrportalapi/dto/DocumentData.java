package az.hrportal.hrportalapi.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class DocumentData {
    @NotNull
    @Range(min = 1, max = 10)
    Integer documentType;
    @NotNull
    Integer employeeId;
}
