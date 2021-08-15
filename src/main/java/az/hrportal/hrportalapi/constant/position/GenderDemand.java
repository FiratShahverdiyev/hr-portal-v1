package az.hrportal.hrportalapi.constant.position;

public enum GenderDemand {
    MALE(1),
    FEMALE(2),
    NON(3);

    private int value;

    GenderDemand(int value) {
        this.value = value;
    }

    private GenderDemand intToEnum(int value) {
        GenderDemand[] values = GenderDemand.values();
        for (GenderDemand demand : values) {
            if (demand.value == value)
                return demand;
        }
        return NON;
    }
}
