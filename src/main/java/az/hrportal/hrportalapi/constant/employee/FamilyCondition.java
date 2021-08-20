package az.hrportal.hrportalapi.constant.employee;

public enum FamilyCondition {
    MARRIED(1),
    DIVORCED(2),
    WIDOW(3),
    SINGLE(4);

    private int condition;

    public int getCondition() {
        return condition;
    }

    FamilyCondition(int condition) {
        this.condition = condition;
    }

    public static FamilyCondition intToEnum(int value) {
        FamilyCondition[] values = FamilyCondition.values();
        for (FamilyCondition familyCondition : values) {
            if (familyCondition.condition == value)
                return familyCondition;
        }
        throw new RuntimeException();
    }
}
