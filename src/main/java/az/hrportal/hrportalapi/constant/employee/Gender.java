package az.hrportal.hrportalapi.constant.employee;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("Kişi"),
    FEMALE("Qadın");

    private String value;

    Gender(String value) {
        this.value = value;
    }
}
