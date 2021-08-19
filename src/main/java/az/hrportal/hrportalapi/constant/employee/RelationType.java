package az.hrportal.hrportalapi.constant.employee;

public enum RelationType {
    BACI(1),
    QARDAS(2),
    ANA(3),
    ATA(4);

    private int value;

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
