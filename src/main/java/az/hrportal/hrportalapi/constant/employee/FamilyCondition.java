package az.hrportal.hrportalapi.constant.employee;

public enum FamilyCondition {
    MARRIED(1),
    DIVORCED(2),
    WIDOW(3),
    SINGLE(4);

    private int condition;

    FamilyCondition(int condition) {
        this.condition = condition;
    }

    public FamilyCondition intToEnum(int value) {
        FamilyCondition[] values = FamilyCondition.values();
        for (FamilyCondition familyCondition : values) {
            if (familyCondition.condition == value)
                return familyCondition;
        }
        return SINGLE;
    }
}
