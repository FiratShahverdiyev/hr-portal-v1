package az.hrportal.hrportalapi.constant.employee;

import lombok.Getter;

@Getter
public enum FamilyCondition {
    MARRIED("Evli"),
    DIVORCED("Boşanmış"),
    WIDOW("Dul"),
    SINGLE("Subay");

    private String value;

    FamilyCondition(String value) {
        this.value = value;
    }
}
