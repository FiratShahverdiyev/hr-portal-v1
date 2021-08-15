package az.hrportal.hrportalapi.constant.position;

public enum WorkMode {
    DAILY(1),
    ALTERNATELY(2);

    private int value;

    WorkMode(int value) {
        this.value = value;
    }

    private WorkMode intToEnum(int value) {
        WorkMode[] values = WorkMode.values();
        for (WorkMode workMode : values) {
            if (workMode.value == value)
                return workMode;
        }
        return DAILY;
    }
}