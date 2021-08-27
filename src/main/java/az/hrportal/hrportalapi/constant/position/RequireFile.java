package az.hrportal.hrportalapi.constant.position;

public enum RequireFile {
    CERTIFICATE("Sertifikat"),
    CARD("Vəsiqə");

    private String value;

    RequireFile(String value) {
        this.value = value;
    }
}
