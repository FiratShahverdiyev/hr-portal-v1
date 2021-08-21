package az.hrportal.hrportalapi.constant.position;

import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;

public enum Level {
    NONE(-1),
    BEST(1),
    MIDDLE(2),
    GOOD(3);

    private int value;

    Level(int value) {
        this.value = value;
    }

    public static Level intToEnum(int value) {
        Level[] values = Level.values();
        for (Level level : values) {
            if (level.value == value)
                return level;
        }
        throw new EnumNotFoundException(Level.class, value);
    }
}
