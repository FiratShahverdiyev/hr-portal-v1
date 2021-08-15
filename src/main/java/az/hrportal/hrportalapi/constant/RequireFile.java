package az.hrportal.hrportalapi.constant;

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
        return CERTIFICATE;
    }
}
