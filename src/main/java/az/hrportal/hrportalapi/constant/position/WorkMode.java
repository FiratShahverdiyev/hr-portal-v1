package az.hrportal.hrportalapi.constant.position;

import lombok.Getter;

@Getter
public enum WorkMode {
    DAILY("Gündəlik"),
    ALTERNATELY("Növbəli");

    private String value;

    WorkMode(String value) {
        this.value = value;
    }
}
