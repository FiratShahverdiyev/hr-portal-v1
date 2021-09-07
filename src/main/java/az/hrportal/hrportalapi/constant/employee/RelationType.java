package az.hrportal.hrportalapi.constant.employee;

import lombok.Getter;

@Getter
public enum RelationType {
    SISTER("Bacı"),
    BROTHER("Qardaş"),
    MOTHER("Ana"),
    FATHER("Ata");

    private String value;

    RelationType(String value) {
        this.value = value;
    }
}
