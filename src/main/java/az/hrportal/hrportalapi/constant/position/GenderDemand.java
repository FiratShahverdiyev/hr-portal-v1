package az.hrportal.hrportalapi.constant.position;

import az.hrportal.hrportalapi.constant.employee.BloodGroup;
import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;

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
        throw new EnumNotFoundException(GenderDemand.class, value);
    }
}
