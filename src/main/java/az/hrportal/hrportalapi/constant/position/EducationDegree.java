package az.hrportal.hrportalapi.constant.position;

import lombok.Getter;

@Getter
public enum EducationDegree {
    MIDDLE("Orta təhsil"),
    MIDDLE_SPECIAL("Orta ixtisas təhsili"),
    PROFESSION("Peşə təhsili"),
    BACHELOR("Bakalavr təhsili"),
    MASTER("Magistratura təhsili"),
    DOCTORAL("Doktorantura təhsili");

    private String value;

    EducationDegree(String value) {
        this.value = value;
    }
}
