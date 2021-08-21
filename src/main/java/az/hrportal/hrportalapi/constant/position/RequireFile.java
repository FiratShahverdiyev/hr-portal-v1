package az.hrportal.hrportalapi.constant.position;

import az.hrportal.hrportalapi.constant.employee.BloodGroup;
import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;

public enum RequireFile {
    CERTIFICATE(1),
    CARD(2);

    private int value;

    RequireFile(int value) {
        this.value = value;
    }

    private RequireFile intToEnum(int value) {
        RequireFile[] values = RequireFile.values();
        for (RequireFile requireFile : values) {
            if (requireFile.value == value)
                return requireFile;
        }
        throw new EnumNotFoundException(RequireFile.class, value);
    }
}
