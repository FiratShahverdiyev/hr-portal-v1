package az.hrportal.hrportalapi.constant.employee;

import lombok.Getter;

@Getter
public enum MilitaryAchievement {
    MILITARY_SUCCESSFULLY("Hərbi mükəlləfiyyətli"),
    UNFIT("Yararsız"),
    LIMITED_USEFUL("Məhdud yararlı"),
    MILITARY_DATE("Hərbidə olma tarixi");

    private String value;

    MilitaryAchievement(String value) {
        this.value = value;
    }
}
