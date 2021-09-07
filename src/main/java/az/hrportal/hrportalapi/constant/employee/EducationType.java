package az.hrportal.hrportalapi.constant.employee;

import lombok.Getter;

@Getter
public enum EducationType {
    VISUAL("Əyani"),
    CORRESPONDENCE("Qiyabi");

    private String value;

    EducationType(String value) {
        this.value = value;
    }
}
