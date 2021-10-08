package az.hrportal.hrportalapi.helper;

import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.operation.Operation;
import az.hrportal.hrportalapi.domain.position.Position;
import az.hrportal.hrportalapi.error.exception.DocumentException;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PdfCreator {
    private PdfFont regular;
    private PdfFont bold;

    public void createFont() {
        regular = getTTInterphasesFont(false);
        bold = getTTInterphasesFont(true);
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfCreatePosition(Document document, Operation operation) {
        log.info("pdfCreatePosition PDF creator started with operationId : {}",
                operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Ştat vahid (lər) inin təsis edilməsi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("Əmrin əsası: " + operation.getMainOfOrder());
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Position position = operation.getPosition();
        Text text1 = new Text("1. Aşağıda qeyd olunan Cəmiyyətin struktur bölməsində qeyd olunan əmək haqqı ilə ştat" +
                " vahidi vahidləri təsis edilsin.");
        Text subText1 = new Text("Ştat cədvəli dəyişiklik edilən struktur bölmə: " +
                position.getDepartment().getName());
        Text subText2 = new Text("Tabe struktur bölmənin adı: ");
        Text subText3 = new Text("Ştat vahidinin adı (vəzifə): " + position.getVacancy().getName());
        Text subText4 = new Text("Ştat vahidi (say):   " + position.getCount());
        Text subText5 = new Text("Əmək haqqı AZN(vergilər və digər ödənişlər daxil olmaqla): " +
                position.getSalary().getSalary());
        Text subText6 = new Text("İş rejimi: " + position.getWorkMode().getValue());
        Text subText7 = new Text("Təsis edilən vəzifənin kateqoriyası: " + position.getVacancyCategory().getValue());
        Text subText8 = new Text("İş yerinin ünvanı: " + position.getWorkPlace());
        Text text2 = new Text("2. Maliyyə və İnsan resursları departamentinə tapşırılsın ki, " +
                "mrdən irəli gələn zəruri məsələlərin həllini təmin etsinlər.");
        Text text3 = new Text("3. Əmrin icrasına nəzarət Baş direktorun müavini Söhrab İsmayılova həvalə edilsin. ");
        Text text4 = new Text("4. Əmr imzalandığı gündən qüvvəyə minir. ");
        Text text5 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        List list = new List()
                .setSymbolIndent(12).setFont(bold)
                .setListSymbol("\u2022");

        list
                .add(new ListItem(subText1.getText()))
                .add(new ListItem(subText2.getText()))
                .add(new ListItem(subText3.getText()))
                .add(new ListItem(subText4.getText()))
                .add(new ListItem(subText5.getText()))
                .add(new ListItem(subText6.getText()))
                .add(new ListItem(subText7.getText()))
                .add(new ListItem(subText8.getText()))
                .setMarginLeft(5);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text1));
        document.add(list);
        document.add(new Paragraph(text2));
        document.add(new Paragraph(text3));
        document.add(new Paragraph(text4));
        document.add(new Paragraph(text5));
        log.info("********** pdfCreatePosition PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfDeletePosition(Document document, Operation operation) {
        log.info("pdfDeletePosition PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Ştat vahid(lər)inin ləğv edilməsi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("Əmrin əsası: " + operation.getMainOfOrder());
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Position position = operation.getPosition();
        Text text1 = new Text("1. Aşağıda qeyd olunan Cəmiyyətin struktur bölməsində qeyd olunan ştat vahidi (ləri)" +
                " ləğv edilsin. ");
        Text subText1 = new Text("Ştat cədvəli dəyişiklik edilən struktur bölmə: " +
                position.getDepartment().getName());
        Text subText2 = new Text("Tabe struktur bölmənin adı: ");
        Text subText3 = new Text("Ştat vahidinin adı (vəzifə): " + position.getVacancy().getName());
        Text subText4 = new Text("Ştat vahidi (say):   " + position.getCount());
        Text subText5 = new Text("Əmək haqqı AZN(vergilər və digər ödənişlər daxil olmaqla): " +
                position.getSalary().getSalary());
        Text subText6 = new Text("İş rejimi: " + position.getWorkMode().getValue());
        Text subText7 = new Text("İş yerinin ünvanı: " + position.getWorkPlace());
        Text text2 = new Text("2. Maliyyə və İnsan resursları departamentinə tapşırılsın ki, " +
                "mrdən irəli gələn zəruri məsələlərin həllini təmin etsinlər.");
        Text text3 = new Text("3. Əmrin icrasına nəzarət Baş direktorun müavini Söhrab İsmayılova həvalə edilsin. ");
        Text text4 = new Text("4. Əmr imzalandığı gündən qüvvəyə minir. ");
        Text text5 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);
        text5.setFont(bold);

        List list = new List()
                .setSymbolIndent(12).setFont(bold)
                .setListSymbol("\u2022");

        list
                .add(new ListItem(subText1.getText()))
                .add(new ListItem(subText2.getText()))
                .add(new ListItem(subText3.getText()))
                .add(new ListItem(subText4.getText()))
                .add(new ListItem(subText5.getText()))
                .add(new ListItem(subText6.getText()))
                .add(new ListItem(subText7.getText()))
                .setMarginLeft(5);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text1));
        document.add(list);
        document.add(new Paragraph(text2));
        document.add(new Paragraph(text3));
        document.add(new Paragraph(text4));
        document.add(new Paragraph(text5));
        log.info("********** pdfDeletePosition PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfJoinToJob(Document document, Operation operation) {
        log.info("pdfJoinToJob PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“İşə qəbul barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("Əmrin əsası: " + operation.getMainOfOrder());
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Position position = operation.getPosition();

        Text text1 = new Text("1. İşçinin soyadı, adı, atasının adı: " +
                employee.getFullName());
        Text text2 = new Text("2. İşə qəbul olduğu struktur bölmə: " + position.getDepartment().getName());
        Text text3 = new Text("3. İşə qəbul olduğu alt struktur bölmə: " + position.getSubDepartment().getName());
        Text text4 = new Text("4. İşə qəbul olduğu vəzifə:   " + position.getVacancy().getName());
        Text text5 = new Text("5. İşə qəbul tarixi: " + operation.getJoinDate());
        Text text6 = new Text("6. Sınaq müddəti: " + operation.getTestPeriod());
        Text text7 = new Text("7. Əmək haqqı AZN(vergilər və digər ödənişlər daxil olmaqla):");
        Text subText1 = new Text("Ştat üzrə əsas əmək haqqı: " + position.getSalary().getSalary());
        Text subText2 = new Text("Əmək şəraitinə görə əlavə: " + position.getAdditionalSalary());
        Text subText3 = new Text("Digər fərdi əlavə: " + operation.getOwnAdditionalSalary());
        Text text8 = new Text("8. Maliyyə və İnsan resursları departamentinə tapşırılsın ki, " +
                "mrdən irəli gələn zəruri məsələlərin həllini təmin etsinlər.");
        Text text9 = new Text("9. Əmr imzalandığı gündən qüvvəyə minir. ");
        Text text10 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        text1.setFont(bold);
        text2.setFont(bold);
        text3.setFont(bold);
        text4.setFont(bold);
        text5.setFont(bold);
        text6.setFont(bold);
        text7.setFont(bold);
        text10.setFont(bold);

        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list
                .add(new ListItem(subText1.getText()))
                .add(new ListItem(subText2.getText()))
                .add(new ListItem(subText3.getText()))
                .setMarginLeft(5);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text1));
        document.add(new Paragraph(text2));
        document.add(new Paragraph(text3));
        document.add(new Paragraph(text4));
        document.add(new Paragraph(text5));
        document.add(new Paragraph(text6));
        document.add(new Paragraph(text7));
        document.add(list);
        document.add(new Paragraph(text8));
        document.add(new Paragraph(text9));
        document.add(new Paragraph(text10));
        log.info("********** pdfJoinToJob PDF creator completed with operationId : {} **********", operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfEndJob(Document document, Operation operation) {
        log.info("pdfEndJob PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Əmək müqaviləsinə xitam verilməsi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("Əmrin əsası: " + operation.getMainOfOrder());
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Position position = employee.getPosition();
        Text text1 = new Text("1. İşçinin soyadı, adı, atasının adı:  " +
                employee.getFullName());
        Text text2 = new Text("2. İşlədiyi struktur bölmənin adı:  " + position.getDepartment().getName());
        Text text3 = new Text("3. İşlədiyi alt struktur bölmənin adı: " + position.getSubDepartment().getName());
        Text text4 = new Text("4. İşçinin vəzifəsi:  " + position.getVacancy().getName());
        Text text5 = new Text("5. İşdən azad olma tarixi:  " + operation.getDismissalDate());
        Text text6 = new Text("6. İşdən azad olma səbəbi:  " + operation.getDismissalReason());

        Text text7 = new Text("7.İstifadə edilməmiş məzuniyyət \n" +
                "günlərinə görə kompensasiya:  " + "HESABLA");

        Text text9 = new Text("9. Maliyyə Departamentinə tapşırılsın ki, ödəniş məsələlərini həll etsin.");
        Text text10 = new Text("10. İnsan resursları departamentinə tapşırılsın ki, əmrlə işçi tanış edilsin. ");
        Text text11 = new Text("11. Əmr imzalandığı gündən qüvvəyə minir. ");
        Text text12 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text1));
        document.add(new Paragraph(text2));
        document.add(new Paragraph(text3));
        document.add(new Paragraph(text4));
        document.add(new Paragraph(text5));
        document.add(new Paragraph(text6));
        document.add(new Paragraph(text7));
        if (operation.getNotes() != null) {
            for (String note : operation.getNotes()) {
                Text text8 = new Text("8.Qeyd:  " + note);
                document.add(new Paragraph(text8));
            }
        }
        document.add(new Paragraph(text9));
        document.add(new Paragraph(text10));
        document.add(new Paragraph(text11));
        document.add(new Paragraph(text12));
        log.info("********** pdfEndJob PDF creator completed with operationId : {} **********", operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfChangeEmployeeSalary(Document document, Operation operation) {
        log.info("pdfChangeEmployeeSalary PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Əmək haqqı dəyişikliyi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph(operation.getTitleDepartment() + " departamentinin müdiri " +
                operation.getTitleFullName() + " oğlunun təqdimatı, işçinin ərizəsi" +
                " və qüvvədə olan əmək müqaviləsinə" + " bağlanmış əlavəyə əsasən,");
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text1 = new Text("1. İşçinin soyadı, adı, atasının adı:  " +
                employee.getFullName());
        Text text2 = new Text("2. İşlədiyi struktur bölmənin adı:  " + employee.getPosition()
                .getDepartment().getName());
        Text text3 = new Text("3. İşlədiyi vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text4 = new Text("4. Dəyişiklik tarixi:  " + operation.getChangeDate());
        Text text5 = new Text("5. Faktiki əmək haqqı: AZN (vergilər və digər ödənişlər daxil olmaqla)");
        Text subText1 = new Text("Ştat üzrə əsas əmək haqqı: " + employee.getPosition().getSalary().getSalary());
        Text subText2 = new Text("Əmək şəraitinə görə əlavə: " + employee.getPosition().getAdditionalSalary());
        Text subText3 = new Text("Digər fərdi əlavə: " + employee.getOwnAdditionalSalary());

        Text text6 = new Text("6. Keçirildiyi əmək haqqı (Azn) vergilər və digər ödənişlər daxil olmaqla): ");
        Text subText4 = new Text("Ştat üzrə əsas əmək haqqı: " + operation.getNewSalary());
        Text subText5 = new Text("Əmək şəraitinə görə əlavə: " + operation.getNewAdditionalSalary());
        Text subText6 = new Text("Digər fərdi əlavə: " + operation.getNewOwnAdditionalSalary());
        Text text7 = new Text("7. İnsan resursları və Maliyyə departamentlərinə tapşırılsın ki, əmrdən irəli gələn" +
                " zəruri məsələlərin həllini təmin etsinlər. ");
        Text text8 = new Text("8. Əmr imzalandığı gündən qüvvəyə minir. ");
        Text text9 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        List list1 = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list1
                .add(new ListItem(subText1.getText()))
                .add(new ListItem(subText2.getText()))
                .add(new ListItem(subText3.getText()))
                .setMarginLeft(5);

        List list2 = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list2
                .add(new ListItem(subText4.getText()))
                .add(new ListItem(subText5.getText()))
                .add(new ListItem(subText6.getText()))
                .setMarginLeft(5);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text1));
        document.add(new Paragraph(text2));
        document.add(new Paragraph(text3));
        document.add(new Paragraph(text4));
        document.add(new Paragraph(text5));
        document.add(list1);
        document.add(new Paragraph(text6));
        document.add(list2);
        document.add(new Paragraph(text7));
        document.add(new Paragraph(text8));
        document.add(new Paragraph(text9));
        log.info("********** pdfChangeEmployeeSalary PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfChangeEmployeePosition(Document document, Operation operation) {
        log.info("pdfChangeEmployeePosition PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Vəzifə dəyişikliyi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph(operation.getTitleDepartment() + " departamentinin müdiri " +
                operation.getTitleFullName() + " oğlunun təqdimatı, işçinin ərizəsi" +
                " və qüvvədə olan əmək müqaviləsinə" + " bağlanmış əlavəyə əsasən,");
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Position position = employee.getPosition();
        Text text1 = new Text("1. İşçinin soyadı, adı, atasının adı:  " +
                employee.getFullName());
        Text text2 = new Text("2. İşlədiyi struktur bölmənin adı:  " + employee.getPosition()
                .getDepartment().getName());
        Text text3 = new Text("3. İşlədiyi vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text4 = new Text("4. Dəyişiklik tarixi:  " + operation.getChangeDate());
        Text text5 = new Text("5. Keçirildiyi struktur bölmə:   " + position.getDepartment().getName());

        Text text6 = new Text("6.Keçirildiyi vəzifə:  " + position.getVacancy().getName());
        Text text7 = new Text("7. Əmək haqqı AZN(vergilər və digər ödənişlər daxil olmaqla):");
        Text subText1 = new Text("Ştat üzrə əsas əmək haqqı: " + employee.getPosition().getSalary().getSalary());
        Text subText2 = new Text("Əmək şəraitinə görə əlavə: " + employee.getPosition().getAdditionalSalary());
        Text subText3 = new Text("Digər fərdi əlavə: " + employee.getOwnAdditionalSalary());

        Text text8 = new Text("8. Keçirildiyi əmək haqqı (Azn) vergilər və digər ödənişlər daxil olmaqla): ");
        Text subText4 = new Text("Ştat üzrə əsas əmək haqqı: " + position.getSalary().getSalary());
        Text subText5 = new Text("Əmək şəraitinə görə əlavə: " + position.getAdditionalSalary());
        Text subText6 = new Text("Digər fərdi əlavə: " + operation.getNewOwnAdditionalSalary());
        Text text9 = new Text("9. İnsan resursları və Maliyyə departamentlərinə tapşırılsın ki, əmrdən irəli gələn" +
                " zəruri məsələlərin həllini təmin etsinlər. ");
        Text text10 = new Text("10. Əmr imzalandığı gündən qüvvəyə minir. ");
        Text text11 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        List list1 = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list1
                .add(new ListItem(subText1.getText()))
                .add(new ListItem(subText2.getText()))
                .add(new ListItem(subText3.getText()))
                .setMarginLeft(5);

        List list2 = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list2
                .add(new ListItem(subText4.getText()))
                .add(new ListItem(subText5.getText()))
                .add(new ListItem(subText6.getText()))
                .setMarginLeft(5);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text1));
        document.add(new Paragraph(text2));
        document.add(new Paragraph(text3));
        document.add(new Paragraph(text4));
        document.add(new Paragraph(text5));
        document.add(new Paragraph(text6));
        document.add(new Paragraph(text7));
        document.add(list1);
        document.add(new Paragraph(text8));
        document.add(list2);
        document.add(new Paragraph(text9));
        document.add(new Paragraph(text10));
        document.add(new Paragraph(text11));
        log.info("********** pdfChangeEmployeePosition PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfAdditionalSalary(Document document, Operation operation) {
        log.info("pdfAdditionalSalary PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Əmək şəraitinə görə əlavə əmək haqqı barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph(operation.getTitleDepartment() + " departamentinin müdiri " +
                operation.getTitleFullName() + " oğlunun təqdimatı, işçinin ərizəsi" +
                " və qüvvədə olan əmək müqaviləsinə" + " bağlanmış əlavəyə əsasən,");
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text1 = new Text("1. İşçinin soyadı, adı, atasının adı:  " +
                employee.getFullName());
        Text text2 = new Text("2. İşlədiyi struktur bölmənin adı:  " + employee.getPosition()
                .getDepartment().getName());
        Text text3 = new Text("3. İşlədiyi vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text4 = new Text("4. Dəyişiklik tarixi:  " + operation.getChangeDate());
        Text text5 = new Text("5. Faktiki əmək haqqı: AZN (vergilər və digər ödənişlər daxil olmaqla)");
        Text subText1 = new Text("Ştat üzrə əsas əmək haqqı: " + employee.getPosition().getSalary().getSalary());
        Text subText2 = new Text("Əmək şəraitinə görə əlavə: " + employee.getPosition().getAdditionalSalary());

        Text text6 = new Text("6. Keçirildiyi əmək haqqı (Azn) vergilər və digər ödənişlər daxil olmaqla): ");
        Text subText4 = new Text("Ştat üzrə əsas əmək haqqı: " + operation.getNewSalary());
        Text subText5 = new Text("Əmək şəraitinə görə əlavə: " + operation.getNewAdditionalSalary());
        Text text7 = new Text("7. İnsan resursları və Maliyyə departamentlərinə tapşırılsın ki, əmrdən irəli gələn" +
                " zəruri məsələlərin həllini təmin etsinlər. ");
        Text text8 = new Text("8. Əmr imzalandığı gündən qüvvəyə minir. ");
        Text text9 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        List list1 = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list1
                .add(new ListItem(subText1.getText()))
                .add(new ListItem(subText2.getText()))
                .setMarginLeft(5);

        List list2 = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list2
                .add(new ListItem(subText4.getText()))
                .add(new ListItem(subText5.getText()))
                .setMarginLeft(5);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text1));
        document.add(new Paragraph(text2));
        document.add(new Paragraph(text3));
        document.add(new Paragraph(text4));
        document.add(new Paragraph(text5));
        document.add(list1);
        document.add(new Paragraph(text6));
        document.add(list2);
        document.add(new Paragraph(text7));
        document.add(new Paragraph(text8));
        document.add(new Paragraph(text9));
        log.info("********** pdfAdditionalSalary PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfChangeWorkMode(Document document, Operation operation) {
        log.info("pdfChangeWorkMode PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Əmək şəraitinə görə əlavə əmək haqqı barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph(operation.getTitleDepartment() + " departamentinin müdiri " +
                operation.getTitleFullName() + " oğlunun təqdimatı, işçinin ərizəsi" +
                " və qüvvədə olan əmək müqaviləsinə" + " bağlanmış əlavəyə əsasən,");
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text1 = new Text("1. İşçinin soyadı, adı, atasının adı:  " +
                employee.getFullName());
        Text text2 = new Text("2. İşlədiyi struktur bölmənin adı:  " + employee.getPosition()
                .getDepartment().getName());
        Text text3 = new Text("3. İşçinin işlədiyi alt struktur bölmə:  " + employee.getPosition()
                .getSubDepartment().getName());
        Text text4 = new Text("4. İşlədiyi vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text5 = new Text("5. İşçinin faktiki iş rejimi:  " + employee.getPosition().getWorkMode().getValue());
        Text text6 = new Text("6. İşçinin keçirildiyi iş rejimi:  " + operation.getWorkMode().getValue());
        Text text7 = new Text("7. Faktiki əmək haqqı: AZN (vergilər və digər ödənişlər daxil olmaqla) : " +
                employee.getPosition().getSalary().getSalary());
        Text text8 = new Text("8. Dəyişiklik edilən əmək haqqı Azn (vergilər və digər ödənişlər daxil olmaqla: ");
        Text text9 = new Text("9. İnsan resursları və Maliyyə departamentlərinə tapşırılsın ki, əmrdən irəli gələn" +
                " zəruri məsələlərin həllini təmin etsinlər. ");
        Text text10 = new Text("10. Əmr imzalandığı gündən qüvvəyə minir. ");
        Text text11 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text1));
        document.add(new Paragraph(text2));
        document.add(new Paragraph(text3));
        document.add(new Paragraph(text4));
        document.add(new Paragraph(text5));
        document.add(new Paragraph(text6));
        document.add(new Paragraph(text7));
        document.add(new Paragraph(text8));
        document.add(new Paragraph(text9));
        document.add(new Paragraph(text10));
        document.add(new Paragraph(text11));
        log.info("********** pdfChangeWorkMode PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfTemporaryChange(Document document, Operation operation) {
        log.info("pdfTemporaryChange PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Müvəqqəti keçirilmə barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("Azərbaycan Respublikası Əmək Məcəlləsinin 60-cı maddəsini rəhbər" +
                " tutaraq" + operation.getTitleDepartment() + "departamentinin müdiri " + operation.getTitleFullName() +
                " oğlunun təqdimatına əsasən,");
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Position position = operation.getPosition();
        Text text1 = new Text("1. İşçinin soyadı, adı, atasının adı:  " +
                employee.getFullName());
        Text text2 = new Text("2. İşlədiyi struktur bölmənin adı:  " + employee.getPosition()
                .getDepartment().getName());
        Text text3 = new Text("3. İşlədiyi vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text4 = new Text("4. Dəyişiklik tarixi:  " + operation.getChangeDate());
        Text text5 = new Text("5. Keçirildiyi müddət:   " + operation.getChangePeriod());
        Text text6 = new Text("6. Keçirildiyi struktur bölmə: " + position.getDepartment().getName());
        Text text7 = new Text("7. Keçirildiyi alt struktur bölmə: " + position.getSubDepartment().getName());
        Text text8 = new Text("8. Keçirildiyi iş yeri: " + position.getWorkPlace());
        Text text9 = new Text("9. Əmək haqqı AZN(vergilər və digər ödənişlər daxil olmaqla):");
        Text subText1 = new Text("Ştat üzrə əsas əmək haqqı: " + employee.getPosition().getSalary().getSalary());
        Text subText2 = new Text("Əmək şəraitinə görə əlavə: " + employee.getPosition().getAdditionalSalary());
        Text subText3 = new Text("Digər fərdi əlavə: " + employee.getOwnAdditionalSalary());

        Text text10 = new Text("10. Keçirildiyi əmək haqqı (Azn) vergilər və digər ödənişlər daxil olmaqla): ");
        Text subText4 = new Text("Ştat üzrə əsas əmək haqqı: " + position.getSalary().getSalary());
        Text subText5 = new Text("Əmək şəraitinə görə əlavə: " + position.getAdditionalSalary());
        Text subText6 = new Text("Digər fərdi əlavə: " + operation.getNewOwnAdditionalSalary());
        Text text11 = new Text("11. İnsan resursları və Maliyyə departamentlərinə tapşırılsın ki, əmrdən irəli gələn" +
                " zəruri məsələlərin həllini təmin etsinlər. ");
        Text text12 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        List list1 = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list1
                .add(new ListItem(subText1.getText()))
                .add(new ListItem(subText2.getText()))
                .setMarginLeft(5);

        List list2 = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list2
                .add(new ListItem(subText4.getText()))
                .add(new ListItem(subText5.getText()))
                .setMarginLeft(5);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text1));
        document.add(new Paragraph(text2));
        document.add(new Paragraph(text3));
        document.add(new Paragraph(text4));
        document.add(new Paragraph(text5));
        document.add(new Paragraph(text6));
        document.add(new Paragraph(text7));
        document.add(new Paragraph(text8));
        document.add(new Paragraph(text9));
        document.add(list1);
        document.add(new Paragraph(text10));
        document.add(list2);
        document.add(new Paragraph(text11));
        document.add(new Paragraph(text12));
        log.info("********** pdfTemporaryChange PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfFinancialHelp(Document document, Operation operation) {
        log.info("pdfFinancialHelp PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Maddi yardım göstərilməsi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("Əmrin əsası: " + operation.getMainOfOrder());
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text1 = new Text("1. İşçinin soyadı, adı, atasının adı:  " +
                employee.getFullName());
        Text text2 = new Text("2. İşlədiyi struktur bölmənin adı:  " + employee.getPosition()
                .getDepartment().getName());
        Text text3 = new Text("3. İşçinin işlədiyi alt struktur bölmə:  " + employee.getPosition()
                .getSubDepartment().getName());
        Text text4 = new Text("4. İşlədiyi vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text5 = new Text("5. Maddi yardımın məbləği (vergilər və digər ödənişər xaric olmaqla):  " +
                operation.getFinancialHelp());
        Text text6 = new Text("6. İnsan resursları və Maliyyə departamentlərinə tapşırılsın ki, əmrdən irəli gələn" +
                " zəruri məsələlərin həllini təmin etsinlər. ");
        Text text7 = new Text("7. Əmr imzalandığı gündən qüvvəyə minir. ");
        Text text8 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text1));
        document.add(new Paragraph(text2));
        document.add(new Paragraph(text3));
        document.add(new Paragraph(text4));
        document.add(new Paragraph(text5));
        document.add(new Paragraph(text6));
        document.add(new Paragraph(text7));
        document.add(new Paragraph(text8));
        log.info("********** pdfFinancialHelp PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfChangeWorkPlace(Document document, Operation operation) {
        log.info("pdfChangeWorkPlace PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“İş yeri dəyişikliyi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph(operation.getTitleDepartment() + " departamentinin müdiri " +
                operation.getTitleFullName() + " oğlunun təqdimatı, işçinin ərizəsi" +
                " və qüvvədə olan əmək müqaviləsinə" + " bağlanmış əlavəyə əsasən,");
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Position position = employee.getPosition();
        Text text1 = new Text("1. İşçinin soyadı, adı, atasının adı:  " +
                employee.getFullName());
        Text text2 = new Text("2. İşlədiyi struktur bölmənin adı:  " + employee.getPosition()
                .getDepartment().getName());
        Text text3 = new Text("3. İşlədiyi vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text4 = new Text("4. Dəyişiklik tarixi:  " + operation.getChangeDate());
        Text text5 = new Text("5. Keçirildiyi struktur bölmə:   " + position.getDepartment().getName());

        Text text6 = new Text("6.Keçirildiyi iş yeri: " + position.getWorkPlace());
        Text text7 = new Text("7. Əmək haqqı AZN(vergilər və digər ödənişlər daxil olmaqla):");
        Text subText1 = new Text("Ştat üzrə əsas əmək haqqı: " + employee.getPosition().getSalary().getSalary());
        Text subText2 = new Text("Əmək şəraitinə görə əlavə: " + employee.getPosition().getAdditionalSalary());
        Text subText3 = new Text("Digər fərdi əlavə: " + employee.getOwnAdditionalSalary());

        Text text8 = new Text("8. Keçirildiyi əmək haqqı (Azn) vergilər və digər ödənişlər daxil olmaqla): ");
        Text subText4 = new Text("Ştat üzrə əsas əmək haqqı: " + position.getSalary().getSalary());
        Text subText5 = new Text("Əmək şəraitinə görə əlavə: " + position.getAdditionalSalary());
        Text subText6 = new Text("Digər fərdi əlavə: " + operation.getNewOwnAdditionalSalary());
        Text text9 = new Text("9. İnsan resursları və Maliyyə departamentlərinə tapşırılsın ki, əmrdən irəli gələn" +
                " zəruri məsələlərin həllini təmin etsinlər. ");
        Text text10 = new Text("10. Əmr imzalandığı gündən qüvvəyə minir. ");
        Text text11 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        List list1 = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list1
                .add(new ListItem(subText1.getText()))
                .add(new ListItem(subText2.getText()))
                .add(new ListItem(subText3.getText()))
                .setMarginLeft(5);

        List list2 = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list2
                .add(new ListItem(subText4.getText()))
                .add(new ListItem(subText5.getText()))
                .add(new ListItem(subText6.getText()))
                .setMarginLeft(5);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text1));
        document.add(new Paragraph(text2));
        document.add(new Paragraph(text3));
        document.add(new Paragraph(text4));
        document.add(new Paragraph(text5));
        document.add(new Paragraph(text6));
        document.add(new Paragraph(text7));
        document.add(list1);
        document.add(new Paragraph(text8));
        document.add(list2);
        document.add(new Paragraph(text9));
        document.add(new Paragraph(text10));
        document.add(new Paragraph(text11));
        log.info("********** pdfChangeWorkPlace PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfTemporaryPass(Document document, Operation operation) {
        log.info("pdfTemporaryPass PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Müvəqqəti keçirilmə barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph(operation.getTitleDepartment() + " departamentinin müdiri " +
                operation.getTitleFullName() + " oğlunun təqdimatı, işçinin ərizəsi" +
                " və qüvvədə olan əmək müqaviləsinə" + " bağlanmış əlavəyə əsasən,");
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Position position = employee.getPosition();
        Text text1 = new Text("1. İşçinin soyadı, adı, atasının adı:  " +
                employee.getFullName());
        Text text2 = new Text("2. İşlədiyi struktur bölmənin adı:  " + employee.getPosition()
                .getDepartment().getName());
        Text text3 = new Text("3. İşlədiyi vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text4 = new Text("4. Dəyişiklik tarixi:  " + operation.getChangeDate());
        Text text5 = new Text("5. Keçirildiyi müddət:   " + operation.getChangePeriod());
        Text text6 = new Text("6. Keçirildiyi struktur bölmə: " + position.getDepartment().getName());
        Text text7 = new Text("7. Keçirildiyi alt struktur bölmə: " + position.getSubDepartment().getName());
        Text text8 = new Text("8. Keçirildiyi iş yeri: " + position.getWorkPlace());
        Text text9 = new Text("9. Əmək haqqı AZN(vergilər və digər ödənişlər daxil olmaqla):");
        Text subText1 = new Text("Ştat üzrə əsas əmək haqqı: " + employee.getPosition().getSalary().getSalary());
        Text subText2 = new Text("Əmək şəraitinə görə əlavə: " + employee.getPosition().getAdditionalSalary());
        Text subText3 = new Text("Digər fərdi əlavə: " + employee.getOwnAdditionalSalary());

        Text text10 = new Text("10. Keçirildiyi əmək haqqı (Azn) vergilər və digər ödənişlər daxil olmaqla): ");
        Text subText4 = new Text("Ştat üzrə əsas əmək haqqı: " + position.getSalary().getSalary());
        Text subText5 = new Text("Əmək şəraitinə görə əlavə: " + position.getAdditionalSalary());
        Text subText6 = new Text("Digər fərdi əlavə: " + operation.getNewOwnAdditionalSalary());
        Text text11 = new Text("11. İnsan resursları və Maliyyə departamentlərinə tapşırılsın ki, əmrdən irəli gələn" +
                " zəruri məsələlərin həllini təmin etsinlər. ");
        Text text12 = new Text("12. Əmr imzalandığı gündən qüvvəyə minir. ");
        Text text13 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        List list1 = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list1
                .add(new ListItem(subText1.getText()))
                .add(new ListItem(subText2.getText()))
                .add(new ListItem(subText3.getText()))
                .setMarginLeft(5);

        List list2 = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list2
                .add(new ListItem(subText4.getText()))
                .add(new ListItem(subText5.getText()))
                .add(new ListItem(subText6.getText()))
                .setMarginLeft(5);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text1));
        document.add(new Paragraph(text2));
        document.add(new Paragraph(text3));
        document.add(new Paragraph(text4));
        document.add(new Paragraph(text5));
        document.add(new Paragraph(text6));
        document.add(new Paragraph(text7));
        document.add(new Paragraph(text8));
        document.add(new Paragraph(text9));
        document.add(list1);
        document.add(new Paragraph(text10));
        document.add(list2);
        document.add(new Paragraph(text11));
        document.add(new Paragraph(text12));
        document.add(new Paragraph(text13));
        log.info("********** pdfTemporaryPass PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings("checkstyle:localvariablename")
    private PdfFont getTTInterphasesFont(boolean isBold) {
        String TTInterphases;
        if (isBold)
            TTInterphases = "./src/main/resources/fonts/TTInterfaces-Bold.ttf";
        else
            TTInterphases = "./src/main/resources/fonts/TTInterfaces-Regular.ttf";
        try {
            FontProgram fontProgram = FontProgramFactory.createFont(TTInterphases);
            return PdfFontFactory.createFont(fontProgram, PdfEncodings.IDENTITY_H, false);
        } catch (Exception ex) {
            throw new DocumentException(ex.getMessage());
        }
    }
}
