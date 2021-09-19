package az.hrportal.hrportalapi.constant;

import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;
import lombok.Getter;

@Getter
public enum DocumentType {
    SHTAT_VAHIDININ_TESISI(1, DocumentLabel.STAFF.toString()),
    SHTAT_VAHIDININ_LEGVI(2, DocumentLabel.STAFF.toString()),
    SHTAT_EMEK_HAQQININ_DEYISTIRILMESI(3, DocumentLabel.STAFF.toString()),
    STRUKTURUN_TESIS_EDILMESI(4, ""),
    STRUKTURUN_ADININ_DEYISTIRILMESI(5, ""),
    STRUKRUTUN_LEGV_EDILMESI(6, ""),
    ISHE_QEBUL(7, DocumentLabel.EMPLOYEE.toString()),
    XITAM(8, DocumentLabel.EMPLOYEE.toString()),
    VEZIFE_DEYISIKLIYI(9, DocumentLabel.EMPLOYEE.toString()),
    EMEK_HAQQI_DEYISIKLIYI(10, DocumentLabel.EMPLOYEE.toString()),
    ISH_YERI_DEYISIKLIYI(11, DocumentLabel.EMPLOYEE.toString()),
    MUVEQQETI_KECIRILME(12, DocumentLabel.EMPLOYEE.toString()),
    MADDI_YARDIM(13, DocumentLabel.EMPLOYEE.toString()),
    MUKAFATLANDIRMA(14, DocumentLabel.EMPLOYEE.toString());

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
