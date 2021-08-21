package az.hrportal.hrportalapi.constant.employee;

import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;

public enum RelationType {
    SISTER(1),
    BROTHER(2),
    MOTHER(3),
    FATHER(4);

    private int value;

    public int getValue() {
        return value;
    }

    RelationType(int value) {
        this.value = value;
    }

    public static RelationType intToEnum(int value) {
        RelationType[] values = RelationType.values();
        for (RelationType relationType : values) {
            if (relationType.value == value)
                return relationType;
        }
        throw new EnumNotFoundException(RelationType.class, value);
    }
}
