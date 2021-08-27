package az.hrportal.hrportalapi.constant.position;

import lombok.Getter;

@Getter
public enum GenderDemand {
    MALE("Kişi"),
    FEMALE("Qadın"),
    NON("Tələb yoxdur");

    private String value;

    GenderDemand(String value) {
        this.value = value;
    }
}
