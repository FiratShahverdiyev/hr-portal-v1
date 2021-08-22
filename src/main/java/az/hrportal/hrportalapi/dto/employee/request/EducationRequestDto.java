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
    String educationType;
    String nostrifikasiyaNumber;
}
