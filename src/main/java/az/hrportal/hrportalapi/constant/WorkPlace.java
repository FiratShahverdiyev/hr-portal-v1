package az.hrportal.hrportalapi.constant;

public enum WorkPlace {
    Bakı_inzibati_bina(1),
    Dəniz_vağzalı(2),
    Dübəndi_terminalı(3),
    Qaradağ_anbarı(4),
    Ələt(5);

    private int value;

    WorkPlace(int value) {
        this.value = value;
    }

    private WorkPlace intToEnum(int value) {
        WorkPlace[] values = WorkPlace.values();
        for (WorkPlace workPlace : values) {
            if (workPlace.value == value)
                return workPlace;
        }
        return Bakı_inzibati_bina;
    }
}
