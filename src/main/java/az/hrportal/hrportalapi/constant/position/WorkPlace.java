package az.hrportal.hrportalapi.constant.position;

import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;

public enum WorkPlace {
    Bakı_inzibati_bina(1),
    Dəniz_vağzalı(2),
    Dübəndi_terminalı(3),
    Qaradağ_anbarı(4),
    Ələt(5);

    private int value;

    public int getValue() {
        return value;
    }

    WorkPlace(int value) {
        this.value = value;
    }

    public static WorkPlace intToEnum(int value) {
        WorkPlace[] values = WorkPlace.values();
        for (WorkPlace workPlace : values) {
            if (workPlace.value == value)
                return workPlace;
        }
        throw new EnumNotFoundException(WorkPlace.class, value);
    }
}
