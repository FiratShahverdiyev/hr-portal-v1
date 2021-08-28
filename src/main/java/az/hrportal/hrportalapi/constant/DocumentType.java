package az.hrportal.hrportalapi.constant;

import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;

public enum DocumentType {
    SHTAT_VAHIDININ_TESISI(1),
    SHTAT_VAHIDININ_LEGVI(2),
    SHTAT_EMEK_HAQQININ_DEYISTIRILMESI(3),
    STRUKTURUN_TESIS_EDILMESI(4),
    STRUKTURUN_ADININ_DEYISTIRILMESI(5),
    STRUKRUTUN_LEGV_EDILMESI(6),
    ISHE_QEBUL(7),
    XITAM(8),
    VEZIFE_DEYISIKLIYI(9),
    EMEK_HAQQI_DEYISIKLIYI(10);

    private int value;

    DocumentType(int value) {
        this.value = value;
    }

    public static DocumentType intToEnum(int value) {
        DocumentType[] values = DocumentType.values();
        for (DocumentType documentType : values) {
            if (documentType.value == value)
                return documentType;
        }
        throw new EnumNotFoundException(DocumentType.class, value);
    }
}
