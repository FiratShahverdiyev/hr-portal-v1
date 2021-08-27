package az.hrportal.hrportalapi.constant.position;

import lombok.Getter;

@Getter
public enum WorkCondition {
    HARMLESS("Zərərsiz"),
    HARMFUL("Zərərli");

    private String value;

    WorkCondition(String value) {
        this.value = value;
    }
}
