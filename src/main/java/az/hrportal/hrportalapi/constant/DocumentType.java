package az.hrportal.hrportalapi.constant;

import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;
import lombok.Getter;

@Getter
public enum DocumentType {
    SHTAT_VAHIDININ_TESISI(1, Constant.STUFF),
    SHTAT_VAHIDININ_LEGVI(2, Constant.STUFF),
    SHTAT_EMEK_HAQQININ_DEYISTIRILMESI(3, Constant.STUFF),
    STRUKTURUN_TESIS_EDILMESI(4, Constant.STUFF),
    STRUKTURUN_ADININ_DEYISTIRILMESI(5, ""),
    STRUKRUTUN_LEGV_EDILMESI(6, ""),
    ISHE_QEBUL(7, Constant.EMPLOYEE),
    XITAM(8, Constant.EMPLOYEE),
    VEZIFE_DEYISIKLIYI(9, Constant.EMPLOYEE),
    EMEK_HAQQI_DEYISIKLIYI(10, Constant.EMPLOYEE);

    private int value;
    private String label;

    DocumentType(int value, String label) {
        this.label = label;
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
