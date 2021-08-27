package az.hrportal.hrportalapi.constant.position;

import lombok.Getter;

@Getter
public enum Level {
    NONE(""),
    BEST("Əla"),
    MIDDLE("Orta"),
    GOOD("Yaxşı");

    private String value;

    Level(String value) {
        this.value = value;
    }
}
