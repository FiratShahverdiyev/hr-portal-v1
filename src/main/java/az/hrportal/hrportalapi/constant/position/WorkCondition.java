package az.hrportal.hrportalapi.constant.position;

import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;

public enum WorkCondition {
    HARMLESS(1),
    HARMFUL(2);

    private int value;

    WorkCondition(int value) {
        this.value = value;
    }

    public static WorkCondition intToEnum(int value) {
        WorkCondition[] values = WorkCondition.values();
        for (WorkCondition workCondition : values) {
            if (workCondition.value == value)
                return workCondition;
        }
        throw new EnumNotFoundException(WorkCondition.class, value);
    }
}
