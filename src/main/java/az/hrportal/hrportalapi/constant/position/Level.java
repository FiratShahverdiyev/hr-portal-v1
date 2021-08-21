package az.hrportal.hrportalapi.constant.position;

import az.hrportal.hrportalapi.constant.employee.BloodGroup;
import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;

public enum Level {
    BEST(1),
    MIDDLE(2),
    GOOD(3);

    private int value;

    Level(int value) {
        this.value = value;
    }

    private Level intToEnum(int value) {
        Level[] values = Level.values();
        for (Level level : values) {
            if (level.value == value)
                return level;
        }
        throw new EnumNotFoundException(Level.class, value);
    }
}
