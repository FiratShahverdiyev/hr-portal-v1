package az.hrportal.hrportalapi.constant;

import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;
import lombok.Getter;

@Getter
public enum Status {
    PENDING(0),
    APPROVED(1),
    REJECTED(2);

    private int value;

    Status(int value) {
        this.value = value;
    }

    public static Status intToEnum(int value) {
        Status[] values = Status.values();
        for (Status status : values) {
            if (status.value == value)
                return status;
        }
        throw new EnumNotFoundException(Status.class, value);
    }
}
