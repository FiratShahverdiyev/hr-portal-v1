package az.hrportal.hrportalapi.constant.employee;

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
        throw new RuntimeException();
    }
}
