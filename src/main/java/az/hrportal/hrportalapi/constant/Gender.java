package az.hrportal.hrportalapi.constant;

public enum Gender {
    MALE(1),
    FEMALE(2);

    private int gender;

    Gender(int gender) {
        this.gender = gender;
    }

    public Gender intToEnum(int value) {
        Gender[] values = Gender.values();
        for (Gender gender : values) {
            if (gender.gender == value)
                return gender;
        }
        return MALE;
    }
}
