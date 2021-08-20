package az.hrportal.hrportalapi.dto.employee;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EducationRequestDto {
    String academicDegreeDate;
    String academicDegreeNumber;
    String academicDegreeOrganization;
    String institution; //TODO hardan gelir ??
    String faculty;
    String direction;
    String speciality;
    String entranceDate;
    String graduateDate;
    String degree;
    String graduateFileNumber;
    String graduateFileDate;
    Integer educationType;
    String nostrifikasiyaNumber;
}
