package az.hrportal.hrportalapi.constant.employee;

import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;

public enum Gender {
    MALE(1),
    FEMALE(2);

    private int gender;

    public int getGender() {
        return gender;
    }

    Gender(int gender) {
        this.gender = gender;
    }

    public static Gender intToEnum(int value) {
        Gender[] values = Gender.values();
        for (Gender gender : values) {
            if (gender.gender == value)
                return gender;
        }
        throw new EnumNotFoundException(Gender.class, value);
    }
}
