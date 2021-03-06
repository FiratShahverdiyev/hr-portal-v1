package az.hrportal.hrportalapi.constant;

import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;
import lombok.Getter;

@Getter
public enum DocumentType {
    SHTAT_VAHIDININ_TESISI(1, DocumentLabel.STAFF.toString(), "Ştat vahidinin təsisi"),
    SHTAT_VAHIDININ_LEGVI(2, DocumentLabel.STAFF.toString(), "Ştat vahidinin ləğvi"),
    ISHE_QEBUL(7, DocumentLabel.EMPLOYEE.toString(), "İşə qəbul"),
    XITAM(8, DocumentLabel.EMPLOYEE.toString(), "Xitam"),
    VEZIFE_DEYISIKLIYI(9, DocumentLabel.EMPLOYEE.toString(), "Vəzifə dəyişikliyi"),
    EMEK_HAQQI_DEYISIKLIYI(10, DocumentLabel.EMPLOYEE.toString(), "Əmək haqqı dəyişikliyi"),
    ELAVE_EMEK_HAQQI(11, DocumentLabel.EMPLOYEE.toString(), "Əlavə əmək haqqı"),
    ISH_REJIMININ_DEYISTIRILMESI(12, DocumentLabel.EMPLOYEE.toString(), "İş rejiminin dəyiştirilməsi"),
    ISH_YERI_DEYISIKLIYI(13, DocumentLabel.EMPLOYEE.toString(), "Iş yeri dəyişikliyi"),
    MUVEQQETI_KECIRILME(14, DocumentLabel.EMPLOYEE.toString(), "Müvəqqəti keçirilmə"),
    MUVEQQETI_HEVALE(15, DocumentLabel.EMPLOYEE.toString(), "Müvəqqəti həvalə barədə"),
    MUVEQQETI_EVEZETME(16, DocumentLabel.EMPLOYEE.toString(), "Müvəqqəti əvəzetmə"),
    MEZUNIYYET_VERILMESI(17, DocumentLabel.EMPLOYEE.toString(), "İşçiyə əmək məzuniyyəti verilməsi barədə"),
    TEHSIL_YARADICILIQ_MEZUNIYYETI(18, DocumentLabel.EMPLOYEE.toString(), "Təhsil-Yaradıcılıq məzuniyyəti"),
    ISCIYE_ODENISSIZ_MEZUNIYYET(19, DocumentLabel.EMPLOYEE.toString(),
            "İşçiyə ödənişsiz məzuniyyətin verilməsi"),
    ISCIYE_SOSIAL_MEZUNIYYET(20, DocumentLabel.EMPLOYEE.toString(), "İşçiyə sosial məzuniyyət" +
            " verilməsi barədə"),
    QISMEN_ODENISHLI_SOSIAL_MEZUNIYYET(21, DocumentLabel.EMPLOYEE.toString(),
            "İşçiyə qismən ödənişli sosial məzuniyyət verilməsi barədə"),
    ODENISHLI_ISTIRAHET_GUNU(22, DocumentLabel.EMPLOYEE.toString(), "Kollektiv müqaviləyə əsasən " +
            "ödənişli istirahət günü barədə"),
    MEZUNIYYETIN_UZADILMASI(23, DocumentLabel.EMPLOYEE.toString(), "Əmək məzuniyyətinin uzadılması barədə"),
    MEZUNIYYETDEN_GERI_QAYITMA(24, DocumentLabel.EMPLOYEE.toString(), "Əmək məzuniyyətindən geri çağırılma " +
            "barədə"),
    ODENISSIZ_MEZUNIYYETDEN_CAGIRILMA(25, DocumentLabel.EMPLOYEE.toString(),
            "Ödənişsiz məzuniyyətdən geri çağırılma"),
    KOMPENSASIYA_ODENILMESI(26, DocumentLabel.EMPLOYEE.toString(),
            "İstifadə edilməmiş əmək məzuniyyətinə görə kompensasiya ödənilməsi barədə"),
    MEZUNIYYET_QRAFIKININ_TESDIQI(27, DocumentLabel.EMPLOYEE.toString(),
            "Məzuniyyət qrafikinin təsdiqi"),
    TELIME_GONDERILME(28, DocumentLabel.EMPLOYEE.toString(), "Təlimə göndərilmə"),
    TELIM_PLANININ_TESDIQI(29, DocumentLabel.EMPLOYEE.toString(), "Təlim planının təsdiqi"),
    EZAMIYYETE_GONDERILME(30, DocumentLabel.EMPLOYEE.toString(), "İşçinin/işçilərin ezamiyyətə " +
            "göndərilməsi barədə"),
    EZAMIYYETIN_UZADILMASI(31, DocumentLabel.EMPLOYEE.toString(), "Ezamiyyənin müddətinin uzadılması " +
            "barədə"),
    EZAMIYYETDEN_GERI_CAGIRILMA(32, DocumentLabel.EMPLOYEE.toString(), "İşçinin/işçilərin ezamiyyətdən " +
            "geri çağırılması barədə"),
    MADDI_YARDIM(33, DocumentLabel.EMPLOYEE.toString(), "Maddi yardım"),
    MUKAFATLANDIRMA(34, DocumentLabel.EMPLOYEE.toString(), "Mükafatlandırma"),
    SHTAT_EMEK_HAQQINA_ELAVE(35, DocumentLabel.EMPLOYEE.toString(), "Ştat əmək haqqına əlavə"),
    SECKIDE_ISTIRAK(36, DocumentLabel.EMPLOYEE.toString(), "Seçkidə iştirak barədə"),
    HERBI_CAGIRISH(37, DocumentLabel.EMPLOYEE.toString(), "Hərbi çağırış barədə"),
    TEDBIRDE_ISTIRAK(38, DocumentLabel.EMPLOYEE.toString(), "Tədbirdə iştirak"),
    ELAVE_ISTIRAHET_GUNU_VERILMESI(39, DocumentLabel.EMPLOYEE.toString(), "İşçiyə əlavə istirahət günü " +
            "verilməsi barədə"),
    MUAVINETIN_TEYIN_OLUNMASI(40, DocumentLabel.EMPLOYEE.toString(), "İstehsalat qəzası zamanı xəsarət " +
            "alan işçiyə müavinətin təyin olunması barədə"),
    ISCININ_ISDEN_KENARLASDIRILMASI(41, DocumentLabel.EMPLOYEE.toString(),
            "İşçinin işdən kənarlaşdırılması"),
    QEYRI_IS_GUNU(42, DocumentLabel.EMPLOYEE.toString(), "Qeyri iş gününün müəyyən olunması"),
    ISH_SAATININ_QISALDILMASI(43, DocumentLabel.EMPLOYEE.toString(), "İş saatlarının qısaldılması barədə"),
    ISHE_CELB_EDILME(44, DocumentLabel.EMPLOYEE.toString(), "İş vaxtından artıq işə cəlb edilmə " +
            "haqqında / İstirahət və ya bayram günündə işə cəlb edilmə haqqında "),
    XEBERDARLIQ(45, DocumentLabel.EMPLOYEE.toString(), "Xəbərdarlıq"),
    EMEK_HAQQINDAN_TUTULMA(46, DocumentLabel.EMPLOYEE.toString(), "Əmək haqqından tutulma"),
    INTIZAM_TENBEHI(47, DocumentLabel.EMPLOYEE.toString(), "İntizam tənbehinə cəlb edilmə");

    private int value;
    private String label;
    private String valueAz;

    DocumentType(int value, String label, String valueAz) {
        this.label = label;
        this.value = value;
        this.valueAz = valueAz;
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
