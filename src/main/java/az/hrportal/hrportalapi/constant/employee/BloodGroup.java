package az.hrportal.hrportalapi.constant.employee;

import lombok.Getter;

@Getter
public enum BloodGroup {
    ONE("Bir"),
    TWO("İki"),
    THREE("Üç"),
    FOUR("Dörd");

    private String value;

    BloodGroup(String value) {
        this.value = value;
    }
}
