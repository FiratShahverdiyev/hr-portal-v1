package az.hrportal.hrportalapi.constant;

import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;
import lombok.Getter;

@Getter
public enum Status {
    PENDING(0, "Təsdiq gözləyir"),
    APPROVED(1, "Təsdiqlənib"),
    REJECTED(2, "Ləğv edildi");

    private int value;
    private String valueAz;

    Status(int value, String valueAz) {
        this.valueAz = valueAz;
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
