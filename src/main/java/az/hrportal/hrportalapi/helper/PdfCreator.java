package az.hrportal.hrportalapi.helper;

import az.hrportal.hrportalapi.constant.Constant;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.operation.Operation;
import az.hrportal.hrportalapi.domain.position.Position;
import az.hrportal.hrportalapi.error.exception.DocumentException;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.repository.employee.EmployeeRepository;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Slf4j
@Component
@RequiredArgsConstructor
public class PdfCreator {
    private PdfFont regular;
    private PdfFont bold;
    private final EmployeeRepository employeeRepository;

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
        Text text8 = new Text("8. Maliyyə Departamentinə tapşırılsın ki, ödəniş məsələlərini həll etsin.");
        Text text9 = new Text("9. İnsan resursları departamentinə tapşırılsın ki, əmrlə işçi tanış edilsin. ");
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
        if (operation.getNotes() != null) {
            for (String note : operation.getNotes()) {
                Text text0 = new Text("Qeyd:  " + note);
                document.add(new Paragraph(text0));
            }
        }
        document.add(new Paragraph(text11));
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

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    public void pdfEmployeeToTraining(Document document, Operation operation) {
        log.info("pdfEmployeeToTraining PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“İşçinin təlimə göndərilməsi barədə”");
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
        Text text1 = new Text("1. Aşağıda məlumatları qeyd olunan işçinin təlim kursunda iştirakı təmin edilsin.");
        Text subText1 = new Text("İşçinin soyadı, adı, atasının adı: " + employee.getFullName());
        Text subText2 = new Text("İşlədiyi struktur bölmə: " + employee.getPosition().getDepartment().getName());
        Text subText3 = new Text("İşlədiyi alt struktur bölmə: " + employee.getPosition().getSubDepartment().getName());
        Text subText4 = new Text("Vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text subText5 = new Text("Təlimin əhatə etdiyi dövrlər: " + operation.getEventFrom() +
                " / " + operation.getEventTo());
        Text subText6 = new Text("Təlimin adı: " + operation.getEventName());
        Text text2 = new Text("2. Maliyyə departamentinə tapşırılsın ki, işçinin təlimdə olduğu müddətdə Azərbaycan " +
                "Respublikası Əmək Məcəlləsinin 179-cu maddəsinin 2-ci hissəsinin “L” " +
                "bəndinə əsasən orta əmək haqqı saxlanılsın.");
        Text text3 = new Text("3. İnsan resursları departamentinə tapşırılsın ki, əmrdən" +
                " irəli gələn məsələləri həll etsin. ");
        Text text4 = new Text("4. Əmr imzalandığı gündən qüvvəyə minir. ");
        Text text5 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list
                .add(new ListItem(subText1.getText()))
                .add(new ListItem(subText2.getText()))
                .add(new ListItem(subText3.getText()))
                .add(new ListItem(subText4.getText()))
                .add(new ListItem(subText5.getText()))
                .add(new ListItem(subText6.getText()))
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
        log.info("********** pdfEmployeeToTraining PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    public void pdfEmployeeToSelection(Document document, Operation operation) {
        log.info("pdfEmployeeToSelection PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Məntəqə Seçki Komissiyasının tərkibində" +
                " iştirakın təmin edilməsi barədə”");
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
        Text text1 = new Text("1. Aşağıda məlumatları qeyd olunan işçinin Məntəqə Seçki Komissiyasının" +
                " tərkibində iştirakı təmin edilsin.");
        Text subText1 = new Text("İşçinin soyadı, adı, atasının adı: " + employee.getFullName());
        Text subText2 = new Text("İşlədiyi struktur bölmə: " + employee.getPosition().getDepartment().getName());
        Text subText3 = new Text("İşlədiyi alt struktur bölmə: " + employee.getPosition().getSubDepartment().getName());
        Text subText4 = new Text("Vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text subText5 = new Text("Seçkidə iştirak edəcəyi tarixlər: " + operation.getEventFrom() +
                " / " + operation.getEventTo());
        Text subText6 = new Text("Seçkidə iştirak edəcəyi müddət (gün): " + operation.getDayInEvent());
        Text text2 = new Text("2. Maliyyə departamentinə tapşırılsın ki,  bu müddət ərzində işçinin" +
                " orta aylıq əmək haqqının saxlanılmasını təmin etsin.");
        Text text3 = new Text("3. İnsan resursları departamentinə tapşırılsın ki, əmrdən" +
                " irəli gələn məsələləri həll etsin. ");
        Text text4 = new Text("4. Əmr imzalandığı gündən qüvvəyə minir. ");
        Text text5 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list
                .add(new ListItem(subText1.getText()))
                .add(new ListItem(subText2.getText()))
                .add(new ListItem(subText3.getText()))
                .add(new ListItem(subText4.getText()))
                .add(new ListItem(subText5.getText()))
                .add(new ListItem(subText6.getText()))
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
        log.info("********** pdfEmployeeToSelection PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    public void pdfApproveTrainingPlan(Document document, Operation operation) {
        log.info("pdfApproveTrainingPlan PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Bakı Beynəlxalq Dəniz Ticarət Limanı” Qapalı Səhmdar " +
                "Cəmiyyətində Təlim planının təsdiq edilməsi haqqında”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        LocalDate now = LocalDate.now(ZoneId.of(Constant.timeZone));
        Paragraph paragraph2 = new Paragraph("“Bakı Beynəlxalq Dəniz Ticarət Limanı” Qapalı Səhmdar Cəmiyyətində" +
                " (bundan sonra -Cəmiyyət) “" + now.getYear() + "-ci il " +
                "üzrə Təlim planı”nın təsdiq edilməsi məqsədilə, ");
        paragraph2.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Text text1 = new Text("1. “Bakı Beynəlxalq Dəniz Ticarət Limanı”" +
                " Qapalı Səhmdar Cəmiyyətində əməkdaşların peşə" +
                " səviyyəsinin artırılması məqsədi ilə “" + now.getYear() + "-" + getSuffix(now.getYear()) +
                " il üzrə Təlim " +
                "planı” təsdiq edilsin (əlavə olunur).");
        Text text2 = new Text("2. İnsan resursları departamentinə tapşırılsın ki," +
                " “" + now.getYear() + "-ci il üzrə Təlim planı” ilə" +
                " aidiyyəti struktur bölmələr tanış edilsin və il ərzində Təlim planında nəzərdə tutulan" +
                " işçilərin təlimə göndərilməsini təmin etsin.");
        Text text3 = new Text("3. Əmrin icrasına nəzarət Baş direktorun müavini Söhrab İsmayılova həvalə edilsin.");
        Text text4 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text1));
        document.add(new Paragraph(text2));
        document.add(new Paragraph(text3));
        document.add(new Paragraph(text4));
        log.info("********** pdfApproveTrainingPlan PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    public void pdfAttendanceInTraining(Document document, Operation operation) {
        log.info("pdfAttendanceInTraining PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Təlim-məşq toplantısında iştirakın təmin edilməsi barədə”");
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
        Text text1 = new Text("1. Aşağıda məlumatları qeyd olunan işçinin təlim məşq toplantısında" +
                " iştirakı təmin edilsin.");
        Text subText1 = new Text("İşçinin soyadı, adı, atasının adı: " + employee.getFullName());
        Text subText2 = new Text("İşlədiyi struktur bölmə: " + employee.getPosition().getDepartment().getName());
        Text subText3 = new Text("İşlədiyi alt struktur bölmə: " + employee.getPosition().getSubDepartment().getName());
        Text subText4 = new Text("Vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text subText5 = new Text("Təlim-məşq toplantısında iştirak edəcəyi tarixlər:  " + operation.getEventFrom() +
                " / " + operation.getEventTo());
        Text subText6 = new Text("Təlim-məşq toplantısında iştirak edəcəyi günlər: " + operation.getDayInEvent());
        Text text2 = new Text("2. Maliyyə departamentinə tapşırılsın ki,  bu müddət ərzində orta" +
                " aylıq əmək haqqının saxlanılmasını təmin etsin.");
        Text text3 = new Text("3. İnsan resursları departamentinə tapşırılsın ki," +
                " əmrdən irəli gələn zəruri məsələlərin həllini təmin etsin. ");
        Text text4 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list
                .add(new ListItem(subText1.getText()))
                .add(new ListItem(subText2.getText()))
                .add(new ListItem(subText3.getText()))
                .add(new ListItem(subText4.getText()))
                .add(new ListItem(subText5.getText()))
                .add(new ListItem(subText6.getText()))
                .setMarginLeft(5);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text1));
        document.add(list);
        document.add(new Paragraph(text2));
        document.add(new Paragraph(text3));
        document.add(new Paragraph(text4));
        log.info("********** pdfAttendanceInTraining PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    public void pdfDisciplineAction(Document document, Operation operation) {
        log.info("pdfDisciplineAction PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“İntizam tənbehinə cəlb edilmə barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("Əmrin əsası: " + operation.getMainOfOrder() + "AR Əmək Məcəlləsinin " +
                "186.2-ci maddəsini və Cəmiyyətin Daxili Nizam-intizam Qaydalarını rəhbər tutaraq, əmək və icra " +
                "intizamına riayət edilməsini gücləndirmək məqsədilə");
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text1 = new Text("1.Aşağıda adı qeyd olunan işçi intizam tənbehinə cəlb edilsin.");
        Text subText1 = new Text("İşçinin soyadı, adı, atasının adı: " + employee.getFullName());
        Text subText2 = new Text("İşlədiyi struktur bölmə: " + employee.getPosition().getDepartment().getName());
        Text subText3 = new Text("İşlədiyi alt struktur bölmə: " + employee.getPosition().getSubDepartment().getName());
        Text subText4 = new Text("Vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text subText5 = new Text("İntizam tənbehinin növü: (Aşağıda qeyd olunanlardan birini" +
                " seçim etmə imkanı olmalı) " + operation.getDisciplineType().getValue());
        Text subText6 = new Text("Təlim-məşq toplantısında iştirak edəcəyi günlər: " + operation.getDayInEvent());
        Text text2 = new Text("2. İnsan resursları departamentinə tapşırılsın ki, aidiyyəti şəxs əmrlə tanış edilsin.");
        Text text3 = new Text("Əsas:  Struktur bölmə rəhbərinin təqdimatı və işçinin izahatı.");
        Text text4 = new Text("Təqdimat sahibinin soyadı, adı, atasının adı: " + operation.getPresentationOwnerName());
        Text text5 = new Text("Struktur bölmə: " + operation.getPresentationOwnerDepartment());
        Text text6 = new Text("Vəzifəsi: " + operation.getPresentationOwnerPosition());
        Text text7 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list
                .add(new ListItem(subText1.getText()))
                .add(new ListItem(subText2.getText()))
                .add(new ListItem(subText3.getText()))
                .add(new ListItem(subText4.getText()))
                .add(new ListItem(subText5.getText()))
                .add(new ListItem(subText6.getText()))
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
        document.add(new Paragraph(text6));
        document.add(new Paragraph(text7));
        log.info("********** pdfDisciplineAction PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    public void pdfWarning(Document document, Operation operation) {
        log.info("pdfWarning PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“İşçiyə/işçilərə xəbərdarlıq edilməsi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("Əmrin əsası: " + operation.getMainOfOrder() + "AR Əmək Məcəlləsinin " +
                "186.3-cü maddəsini və Cəmiyyətin Daxili Nizam-intizam Qaydalarını rəhbər tutaraq, əmək və icra " +
                "intizamına riayət edilməsini gücləndirmək məqsədilə,    ");
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        String fullNames = "";
        String departments = "";
        String positions = "";
        for (Integer employeeId : operation.getEmployeeIds()) {
            if (fullNames.equals("")) {
                fullNames = fullNames.concat(",");
            }
            if (departments.equals("")) {
                departments = departments.concat(",");
            }
            if (positions.equals("")) {
                positions = positions.concat(",");
            }
            Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
                    new EntityNotFoundException(Employee.class, employeeId));
            fullNames = fullNames.concat(employee.getFullName());
            departments = departments.concat(employee.getPosition().getDepartment().getName());
            positions = positions.concat(employee.getPosition().getVacancy().getName());
        }
        Text text1 = new Text("1.Aşağıda adları qeyd olunan işçiyə(işçilərə) xəbərdarlıq edilsin. ");
        Text text2 = new Text("2.Xəbərdarlıq edilən işçinin və ya işçilərin soyadı, adı, atasının adı: " + fullNames);
        Text text3 = new Text("3.İşçinin və ya işçilərin işlədiyi struktur bölmə: " + departments);
        Text text4 = new Text("4.İşçinin və ya işçilərin vəzifəsi:  " + positions);
        Text text5 = new Text("5.İnsan resursları departamentinə tapşırılsın ki," +
                " aidiyyəti şəxs(lər) əmrlə tanış edilsin.");
        Text text6 = new Text("Əsas:  Struktur bölmə rəhbərinin təqdimatı və işçinin izahatı.");
        Text text7 = new Text("Təqdimat sahibinin soyadı, adı, atasının adı: " + operation.getPresentationOwnerName());
        Text text8 = new Text("Struktur bölmə: " + operation.getPresentationOwnerDepartment());
        Text text9 = new Text("Vəzifəsi: " + operation.getPresentationOwnerPosition());
        Text text10 = new Text("Baş direktor                                                                   Taleh " +
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
        log.info("********** pdfWarning PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfCallBackFromVacation(Document document, Operation operation) {
        log.info("pdfCallBackFromVacation PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Ödənişsiz məzuniyyətdən geri çağırılma barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("İşçinin ərizəsinə əsasən");
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text0 = new Text("Aşağıda adı qeyd olunan işçi məzuniyyətdən geri çağırılsın. ");
        Text text1 = new Text("1. İşçinin soyadı, adı, atasının adı:  " +
                employee.getFullName());
        Text text2 = new Text("2. İşlədiyi struktur bölmənin adı:  " + employee.getPosition()
                .getDepartment().getName());
        Text text3 = new Text("3. İşçinin işlədiyi alt struktur bölmə:  " + employee.getPosition()
                .getSubDepartment().getName());
        Text text4 = new Text("4. İşlədiyi vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text5 = new Text("5. Məzuniyyətə buraxıldığı tarixləri: " + operation.getEventFrom() +
                " / " + operation.getEventTo());
        Text text6 = new Text("6. Geri çağırılma tarixi: " + operation.getCallBackDate());
        Text text7 = new Text("7. Geri çağırılma səbəbi: " + operation.getCallBackReason());
        Text text8 = new Text("8. İnsan resursları və Maliyyə departamentlərinə tapşırılsın ki, əmrdən irəli gələn" +
                " məsələlərin həllini təmin etsinlər. ");
        Text text9 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text0));
        document.add(new Paragraph(text1));
        document.add(new Paragraph(text2));
        document.add(new Paragraph(text3));
        document.add(new Paragraph(text4));
        document.add(new Paragraph(text5));
        document.add(new Paragraph(text6));
        document.add(new Paragraph(text7));
        document.add(new Paragraph(text8));
        if (operation.getNotes() != null) {
            for (String note : operation.getNotes()) {
                Text text = new Text("Qeyd:  " + note);
                document.add(new Paragraph(text));
            }
        }
        document.add(new Paragraph(text9));
        log.info("********** pdfCallBackFromVacation PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    public void pdfApproveVacationChart(Document document, Operation operation) {
        log.info("pdfApproveVacationChart PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Əmək məzuniyyətlərinin verilməsi üçün növbəlilik \n" +
                "cədvəlinin təsdiq edilməsi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        LocalDate now = LocalDate.now(ZoneId.of(Constant.timeZone));
        Paragraph paragraph2 = new Paragraph("“Bakı Beynəlxalq Dəniz Ticarət Limanı” Qapalı Səhmdar Cəmiyyətində" +
                " (bundan sonra “Cəmiyyət” adlandırılacaq) istehsalın və işin normal gedişini tənzimləmək," +
                " məzuniyyətlərin uçotunun düzgün aparılmasını təmin etmək məqsədilə Azərbaycan Respublikası " +
                "Əmək Məcəlləsinin 133-cü maddəsini rəhbər tutaraq,");
        paragraph2.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Text text1 = new Text("1. " + operation.getYear() + "-" + getSuffix(operation.getYear()) +
                " il üzrə Cəmiyyət işçilərinin əmək məzuniyyətlərinin " +
                "verilməsi üçün növbəlilik cədvəli təsdiq edilsin. (Əlavə №1)");
        Text text2 = new Text("2. Növbəlilik cədvəli üzrə işçilərin məzuniyyətdən istifadə edilməsi təmin edilsin.");
        Text text3 = new Text("3. Əmr imzalandığı gündən qüvvəyə minir.");
        Text text4 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text1));
        document.add(new Paragraph(text2));
        document.add(new Paragraph(text3));
        document.add(new Paragraph(text4));
        log.info("********** pdfApproveVacationChart PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfAchievement(Document document, Operation operation) {
        log.info("pdfAchievement PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“İşçinin mükafatlandırılması barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("Əmək funksiyasını yüksək peşəkarlıq səviyyəsində yerinə yetirdiyinə," +
                " üzərinə düşən vəzifə öhdəliklərini tam məsuliyyətlə icra etdiyinə görə, Azərbaycan Respublikası" +
                " Əmək Məcəlləsinin 185-ci maddəsini rəhbər tutaraq, işçini həvəsləndirmək məqsədilə, " +
                operation.getMainOfOrder());
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
        Text text5 = new Text("5. Mükafatın məbləği Azn " + operation.getAchievement() +
                " (vergilər və digər ödənişlər xaric)");
        Text text6 = new Text("6. Maliyyə departamentinə tapşırılsın ki, bu Əmrdən irəli" +
                " gələn məsələlərin həllini təmin etsin.");
        Text text7 = new Text("7. İnsan resursları departamentinə tapşırılsın ki, əmrin surəti ilə" +
                " aidiyyəti şəxs tanış edilsin. ");
        Text text8 = new Text("8. Əmr imzalandığı gündən qüvvəyə minir. ");
        Text text9 = new Text("Baş direktor                                                                   Taleh " +
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
        log.info("********** pdfAchievement PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfPositionAdditionalSalary(Document document, Operation operation) {
        log.info("pdfPositionAdditionalSalary PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Əmək haqqına əlavənin müəyyən edilməsi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("İşçinin fəlsəfə və ya elmlər doktoru " +
                operation.getMainOfOrder() + "" +
                " elmi dərəcəsinə malik olduğunu nəzərə alaraq Azərbaycan Respublikası Əmək Məcəlləsinin 185-ci" +
                " maddəsini rəhbər tutaraq, ştat əmək haqqına fərdi əlavənin edilməsi məqsədilə,");
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
        Text text5 = new Text("5. Fərdi əlavənin məbləği Azn: " + operation.getNewOwnAdditionalSalary() +
                " (vergilər və digər ödənişlər xaric)");
        Text text6 = new Text("6. İnsan resursları departamentinə tapşırılsın ki, əmrin surəti ilə" +
                " aidiyyəti şəxs tanış edilsin. ");
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
        log.info("********** pdfPositionAdditionalSalary PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfCatchFromSalary(Document document, Operation operation) {
        log.info("pdfCatchFromSalary PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Əmək haqqından tutulma barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph(operation.getMainOfOrder() + " Göstərilənləri nəzərə alaraq," +
                " müəssisədaxili intizam qaydalarını " +
                "gücləndirmək və dəymiş maddi ziyanın bərpa olunması məqsədilə");
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text0 = new Text("1. Aşağıda adı qeyd olunan işçinin aylıq əmək haqqından qeyd olunan məbləğ tutulsun.");
        Text text1 = new Text("1.1 İşçinin soyadı, adı, atasının adı:  " +
                employee.getFullName());
        Text text2 = new Text("1.2. İşlədiyi struktur bölmənin adı:  " + employee.getPosition()
                .getDepartment().getName());
        Text text3 = new Text("1.3. İşçinin işlədiyi alt struktur bölmə:  " + employee.getPosition()
                .getSubDepartment().getName());
        Text text4 = new Text("1.4. İşlədiyi vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text5 = new Text("1.5. Tutulma məbləği: " + operation.getCatchAmount());
        Text text6 = new Text("1.6. Tutulacağı aylar: " + operation.getCatchMonths());
        Text text7 = new Text("2. Maliyyə Departamentinə tapşırılsın ki," +
                " əmək haqqından tutulma məsələlərini həll etsin. ");
        Text text8 = new Text("Əsas: İşçinin izahatı və razılıq ərizəsi. ");
        Text text9 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text0));
        document.add(new Paragraph(text1));
        document.add(new Paragraph(text2));
        document.add(new Paragraph(text3));
        document.add(new Paragraph(text4));
        document.add(new Paragraph(text5));
        document.add(new Paragraph(text6));
        document.add(new Paragraph(text7));
        document.add(new Paragraph(text8));
        document.add(new Paragraph(text9));
        log.info("********** pdfCatchFromSalary PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfEmployeeShift(Document document, Operation operation) {
        log.info("pdfEmployeeShift PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“İşçinin işdən kənarlaşdırılması barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph(operation.getMainOfOrder());
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text0 = new Text("1. Aşağıda məlumatları qeyd olunan işçi işdən kənarlaşdırılsın.");
        Text text1 = new Text("1.1 İşçinin soyadı, adı, atasının adı:  " +
                employee.getFullName());
        Text text2 = new Text("1.2. İşlədiyi struktur bölmənin adı:  " + employee.getPosition()
                .getDepartment().getName());
        Text text3 = new Text("1.3. İşçinin işlədiyi alt struktur bölmə:  " + employee.getPosition()
                .getSubDepartment().getName());
        Text text4 = new Text("1.4. İşlədiyi vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text5 = new Text("1.5. Kənarlaşdırma müddəti: " + operation.getEventFrom() +
                " / " + operation.getEventTo());
        Text text6 = new Text("2.Maliyyə departamentinə tapşırılsın ki," +
                " Azərbaycan Respublikası Əmək Məcəlləsinin 62-ci" +
                " maddəsinə uyğun olaraq, həmin işçiyə işdən kənar edilən vaxt ərzində əmək haqqı ödənilməsin.");
        Text text7 = new Text("3. İnsan resursları departamentinə tapşırılsın ki, bu əmrdən irəli " +
                "gələn zəruri məsələlərin həllini təmin etsin. ");
        Text text8 = new Text("4. Əmrin icrasına nəzarət Baş direktorun müavini Söhrab İsmayılova həvalə edilsin.");
        Text text9 = new Text("5. Bu əmr imzalandığı tarixdən qüvvəyə minir.");
        Text text10 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text0));
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
        log.info("********** pdfEmployeeShift PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfNonActiveDay(Document document, Operation operation) {
        log.info("pdfNonActiveDay PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Qeyri iş gününün müəyyən olunması barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph(operation.getMainOfOrder());
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Text text0 = new Text("1.İşçilərin əmək haqqı saxlanılmaqla " + operation.getYear() + "-" +
                getSuffix(operation.getYear()) + " il tarixi “Bakı Beynəlxalq Dəniz Ticarət Limanı” QSC- də " +
                "qeyri iş günü hesab edilsin.");
        Text text1 = new Text("2.İnsan resursları departamenti və Maliyyə departamentinə tapşırılsın ki, bu " +
                "Əmrdən irəli gələn məsələlərin həllini təmin etsin.");
        Text text2 = new Text("3.Əmr imzalandığı gündən qüvvəyə minir. ");
        Text text3 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text0));
        document.add(new Paragraph(text1));
        document.add(new Paragraph(text2));
        document.add(new Paragraph(text3));
        log.info("********** pdfNonActiveDay PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfTemporaryAssignment(Document document, Operation operation) {
        log.info("pdfTemporaryAssignment PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Müvəqqəti həvalə barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("Azərbaycan Respublikası Əmək Məcəlləsinin 61-ci maddəsinin " +
                "1-ci bəndini rəhbər tutaraq " + operation.getTitleDepartment() + " departamentinin müdiri " +
                operation.getTitleFullName() + " oğlunun təqdimatı,və işçinin ərizəsinə əsasən,");
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
        Text text2 = new Text("2. İşlədiyi struktur bölmə:  " + employee.getPosition()
                .getDepartment().getName());
        Text text3 = new Text("3. İşlədiyi vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text4 = new Text("4. Dəyişiklik tarixi:  " + operation.getChangeDate());
        Text text5 = new Text("5. Həvalə müddəti:    " + operation.getAssignmentDate());
        Text text6 = new Text("6. Həvalə olunan vəzifə:   " + position.getVacancy().getName());
        Text text7 = new Text("7. Həvalə olunan vəzifənin aid olduğu struktur bölmə: " + position.getDepartment());
        Text text8 = new Text("8. Həvalə olunan vəzifənin aid olduğu alt struktur bölmə: " + position.getSubDepartment()
                .getName()).setBold();
        Text text9 = new Text("9. Keçirildiyi iş yeri: " + position.getWorkPlace().getValue()).setBold();
        Text text10 = new Text("10. Əvəz edən işçinin əmək haqqı: " + employee.getSalary()).setBold();
        Text text11 = new Text("11. Əvəz edilən işçinin əmək haqqı:  " + operation.getAlternateWorkerSalary()).setBold();
        Text text12 = new Text("12. Ödəniləcək əmək haqqı: AZN (vergilər və digər ödənişlər daxil olmaqla)").setBold();

        Paragraph paragraph4;
        if (operation.getAlternateWorkerSalary() > employee.getSalary()) {
            paragraph4 = new Paragraph("12.1.Əvəz edilən işçinin maaşı ilə əvəz edən işçinin maaşı arasındakı " +
                    "fərq ödənilsin.").setMarginLeft(50);
        } else {
            paragraph4 = new Paragraph("12.2.İşçiyə" + operation.getAmount() + " məbləğdə əlavə əmək " +
                    "haqqı ödənilsin. ");
        }
        Text text13 = new Text("13.İnsan resursları və Maliyyə departamentlərinə tapşırılsın ki, əmrdən irəli gələn " +
                "zəruri məsələlərin həllini təmin etsinlər. ");

        Text text14 = new Text("Baş direktor                                                                   Taleh " +
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
        document.add(new Paragraph(text12));
        document.add(paragraph4);
        document.add(new Paragraph(text13));
        document.add(new Paragraph(text14));
        log.info("********** pdfTemporaryAssignment PDF creator completed with operationId : {} **********",
                operation.getId());
    }


    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfGoOnBusinessTrip(Document document, Operation operation) {
        log.info("pdfGoOnBusinessTrip PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“İşçinin/işçilərin ezamiyyətə göndərilməsi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph(operation.getMainOfOrder());

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text1 = new Text("Aşağıda məlumatları qeyd olunan işçi(lər) ezam edilsin. ");
        Text text2 = new Text("1. İşçi (lər)nin soyadı, adı, atasının adı " + employee.getFullName());
        Text text3 = new Text("2. İşlədiyi struktur bölmə: " + employee.getPosition().getDepartment().getName());
        Text text4 = new Text("3. İşlədiyi alt struktur bölmə: " + employee.getPosition().getSubDepartment().getName());
        Text text5 = new Text("4. Vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text6 = new Text("5. Ezam olunduğu ölkə/şəhər/rayon: " + operation.getBusinessTripLocation());
        Text text7 = new Text("6. Ezam olunma tarixi: " + operation.getBusinessTripDate());
        Text text8 = new Text("7. Ezamiyyət müddəti: " + operation.getBusinessTripTerm());
        Text text9 = new Text("8. Ezamiyyə müddətində işçinin yolda keçirdiyi istirahət gününə təsadüf etdiyi tarix: "
                + operation.getNonWorkDay());
        Text text10 = new Text("9. Ezamiyyə müddətində yolda keçirilmiş istirahət gününün əvəzinə verilmiş " +
                "istirahət günü: " + operation.getGivenNonWorkDay());
        Text text11 = new Text("10. İşçinin işə başlama tarixi" + operation.getStartDateToWork());
        Text text13 = new Text("11. Maliyyə departamentinə tapşırılsın ki, Qanunvericiliyə uyğun olaraq ezamiyyə" +
                " xərclərinin ödənilməsini təmin etsin.");
        Text text14 = new Text("12. İnsan resursları departamentinə tapşırılsın ki, əmrdən irəli gələn məsələləri" +
                " həll etsin. ");
        Text text15 = new Text("13. Əmr imzalandığı gündən qüvvəyə minir.");
        Text text16 = new Text("Baş direktor                                                                   Taleh " +
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
        if (operation.getNotes() != null) {
            for (String note : operation.getNotes()) {
                Text text12 = new Text("Qeyd:  " + note);
                document.add(new Paragraph(text12));
            }
        }
        document.add(new Paragraph(text13));
        document.add(new Paragraph(text14));
        document.add(new Paragraph(text15));
        document.add(new Paragraph(text16));
        log.info("********** pdfGoOnBusinessTrip PDF creator completed with operationId : {} **********",
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

    private String getSuffix(Integer year) {
        int lastDigit = year % 10;
        switch (lastDigit) {
            case 0:
            case 9:
                return "cu";
            case 1:
            case 2:
            case 5:
            case 7:
            case 8:
                return "ci";
            case 3:
            case 4:
                return "cü";
            case 6:
                return "cı";
            default:
                return "";
        }
    }
}
