package az.hrportal.hrportalapi.constant.position;

import lombok.Getter;

@Getter
public enum VacancyCategory {
    LEADER("Rəhbər"),
    ENGINEER("Mütəxəssis"),
    TECHNICAL_EXECUTOR("Texniki icraçı"),
    WORKER("Fəhlə");

    private String value;

    VacancyCategory(String value) {
        this.value = value;
    }
}
