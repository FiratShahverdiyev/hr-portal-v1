package az.hrportal.hrportalapi.constant.employee;

import lombok.Getter;

@Getter
public enum Series {
    AZE(""),
    AA("");

    private String value;

    Series(String value) {
        this.value = value;
    }
}
