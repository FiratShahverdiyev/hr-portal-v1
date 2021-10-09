package az.hrportal.hrportalapi.constant;

import lombok.Getter;

@Getter
public enum DisciplineType {
    TOHMET("Töhmət"),
    SIDDETLI_TOHMET("Şiddətli Töhmət");

    private String value;

    DisciplineType(String value) {
        this.value = value;
    }
}
