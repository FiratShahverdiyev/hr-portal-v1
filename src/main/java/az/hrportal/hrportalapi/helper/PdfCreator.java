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
import java.time.temporal.ChronoUnit;

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
                position.getSalary().getAmount());
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
                position.getSalary().getAmount());
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
        Text subText1 = new Text("Ştat üzrə əsas əmək haqqı: " + position.getSalary().getAmount());
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
        Text subText1 = new Text("Ştat üzrə əsas əmək haqqı: " + employee.getPosition().getSalary().getAmount());
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
        Text subText1 = new Text("Ştat üzrə əsas əmək haqqı: " + employee.getPosition().getSalary().getAmount());
        Text subText2 = new Text("Əmək şəraitinə görə əlavə: " + employee.getPosition().getAdditionalSalary());
        Text subText3 = new Text("Digər fərdi əlavə: " + employee.getOwnAdditionalSalary());

        Text text8 = new Text("8. Keçirildiyi əmək haqqı (Azn) vergilər və digər ödənişlər daxil olmaqla): ");
        Text subText4 = new Text("Ştat üzrə əsas əmək haqqı: " + position.getSalary().getAmount());
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
        Text subText1 = new Text("Ştat üzrə əsas əmək haqqı: " + employee.getPosition().getSalary().getAmount());
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
        Text text5 = new Text("5. İşçinin faktiki iş rejimi:  " + employee.getWorkMode().getValue());
        Text text6 = new Text("6. İşçinin keçirildiyi iş rejimi:  " + operation.getWorkMode().getValue());
        Text text7 = new Text("7. Faktiki əmək haqqı: AZN (vergilər və digər ödənişlər daxil olmaqla) : " +
                employee.getGrossSalary());
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
        Paragraph paragraph1 = new Paragraph("“Boş vəzifə üzrə müvəqqəti əvəzetmə barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("Azərbaycan Respublikası Əmək Məcəlləsinin 61-ci maddəsinin " +
                "1-ci bəndini rəhbər tutaraq " + operation.getTitleDepartment() + " departamentinin müdiri " +
                operation.getTitleFullName() + " oğlunun təqdimatı və işçinin ərizəsinə əsasən,");
        paragraph2.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Position position = operation.getPosition();
        Text text1 = new Text("1. İşçinin soyadı, adı, atasının adı:  " + employee.getFullName());
        Text text2 = new Text("2. İşlədiyi struktur bölmə:  " + employee.getPosition()
                .getDepartment().getName());
        Text text3 = new Text("3. İşlədiyi vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text4 = new Text("4. Dəyişiklik tarixi:  " + operation.getChangeDate());
        Text text5 = new Text("5. Əvəzetmə müddəti:   " + operation.getNewTerm());
        Text text6 = new Text("6. Əvəz olunan vəzifə: " + operation.getPosition().getVacancy().getName());
        Text text7 = new Text("7. Əvəz olunan vəzifənin aid olduğu struktur bölmə: " +
                position.getDepartment().getName());
        Text text8 = new Text("8. Əvəz olunan vəzifənin aid olduğu alt struktur bölmə: " +
                position.getSubDepartment().getName());
        Text text9 = new Text("9. Əvəz olunan vəzifənin aid olduğu iş yeri: " +
                position.getWorkPlace().getValue());
        Text text10 = new Text("10. Əvəz edən işçinin əmək haqqı: " + employee.getGrossSalary());
        Text text11 = new Text("11. Əvəz edilən vəzifənin əmək haqqı:" +
                position.getSalary().getAmount());
        Text text12 = new Text("12. Ödəniləcək əmək haqqı: ");
        Text text13 = new Text("12.1. İşçiyə boş vəzifə üçün nəzərdə tutulmuş əmək haqqının (vəzifə maaşının) " +
                "50% miqdarında əlavə əmək haqqı ödənilsin.");
        Text text14 = new Text("13. İnsan resursları və Maliyyə departamentlərinə tapşırılsın ki, əmrdən irəli gələn " +
                "zəruri məsələlərin həllini təmin etsinlər.");
        Text text15 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        List list1 = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list1
                .add(new ListItem(text13.getText()))
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
        document.add(new Paragraph(text10));
        document.add(new Paragraph(text11));
        document.add(new Paragraph(text12));
        document.add(list1);
        document.add(new Paragraph(text14));
        document.add(new Paragraph(text15));
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
        Text subText1 = new Text("Ştat üzrə əsas əmək haqqı: " + employee.getPosition().getSalary().getAmount());
        Text subText2 = new Text("Əmək şəraitinə görə əlavə: " + employee.getPosition().getAdditionalSalary());
        Text subText3 = new Text("Digər fərdi əlavə: " + employee.getOwnAdditionalSalary());

        Text text8 = new Text("8. Keçirildiyi əmək haqqı (Azn) vergilər və digər ödənişlər daxil olmaqla): ");
        Text subText4 = new Text("Ştat üzrə əsas əmək haqqı: " + position.getSalary().getAmount());
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
        Text text5 = new Text("5. Keçirildiyi müddət: " + getBetweenDates(operation.getEventFrom(),
                operation.getEventTo()));
        Text text6 = new Text("6. Keçirildiyi struktur bölmə: " + position.getDepartment().getName());
        Text text7 = new Text("7. Keçirildiyi alt struktur bölmə: " + position.getSubDepartment().getName());
        Text text8 = new Text("8. Keçirildiyi iş yeri: " + position.getWorkPlace());
        Text text9 = new Text("9. Əmək haqqı AZN(vergilər və digər ödənişlər daxil olmaqla):");
        Text subText1 = new Text("Ştat üzrə əsas əmək haqqı: " + employee.getPosition().getSalary().getAmount());
        Text subText2 = new Text("Əmək şəraitinə görə əlavə: " + employee.getPosition().getAdditionalSalary());
        Text subText3 = new Text("Digər fərdi əlavə: " + employee.getOwnAdditionalSalary());

        Text text10 = new Text("10. Keçirildiyi əmək haqqı (Azn) vergilər və digər ödənişlər daxil olmaqla): ");
        Text subText4 = new Text("Ştat üzrə əsas əmək haqqı: " + position.getSalary().getAmount());
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
        Text subText5 = new Text("İntizam tənbehinin növü: " + operation.getDisciplineType().getValue());
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
        Text text5 = new Text("5. Mükafatın məbləği Azn " + operation.getAchievementAmount() +
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
        Text text5 = new Text("5. Həvalə müddəti: " + getBetweenDates(operation.getEventFrom(),
                operation.getEventTo()));
        Text text6 = new Text("6. Həvalə olunan vəzifə:   " + position.getVacancy().getName());
        Text text7 = new Text("7. Həvalə olunan vəzifənin aid olduğu struktur bölmə: " + position.getDepartment());
        Text text8 = new Text("8. Həvalə olunan vəzifənin aid olduğu alt struktur bölmə: " + position.getSubDepartment()
                .getName()).setBold();
        Text text9 = new Text("9. Keçirildiyi iş yeri: " + position.getWorkPlace().getValue()).setBold();
        Text text10 = new Text("10. Əvəz edən işçinin əmək haqqı: " + employee.getGrossSalary()).setBold();
        Text text11 = new Text("11. Əvəz edilən işçinin əmək haqqı:  " +
                operation.getAlternateWorkerSalary()).setBold();
        Text text12 = new Text("12. Ödəniləcək əmək haqqı: AZN (vergilər və digər ödənişlər daxil olmaqla)").setBold();

        Paragraph paragraph4;
        if (operation.getAlternateWorkerSalary() > employee.getGrossSalary()) {
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
        Text text1 = new Text("Aşağıda məlumatları qeyd olunan işçi(lər) ezam edilsin.");
        Text text2 = new Text("1. İşçi (lər)nin soyadı, adı, atasının adı " + employee.getFullName());
        Text text3 = new Text("2. İşlədiyi struktur bölmə: " + employee.getPosition().getDepartment().getName());
        Text text4 = new Text("3. İşlədiyi alt struktur bölmə: " + employee.getPosition().getSubDepartment().getName());
        Text text5 = new Text("4. Vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text6 = new Text("5. Ezam olunduğu ölkə/şəhər/rayon: " + operation.getBusinessTripLocation());
        Text text7 = new Text("6. Ezam olunma tarixi: " + operation.getEventFromBusinessTripDate() + " / " +
                operation.getEventToBusinessTripDate());
        Text text8 = new Text("7. Ezamiyyət müddəti: " + operation.getBusinessTripTerm());
        Text text9 = new Text("8. Ezamiyyə müddətində işçinin yolda keçirdiyi istirahət gününə təsadüf etdiyi tarix: " +
                operation.getNonWorkDay());
        Text text10 = new Text("9. Ezamiyyə müddətində yolda keçirilmiş istirahət gününün əvəzinə verilmiş " +
                "istirahət günü: " + operation.getGivenNonWorkDay());
        Text text11 = new Text("10. İşçinin işə başlama tarixi" + operation.getJoinDate());
        Text text13 = new Text("12. Maliyyə departamentinə tapşırılsın ki, Qanunvericiliyə uyğun olaraq ezamiyyə" +
                " xərclərinin ödənilməsini təmin etsin.");
        Text text14 = new Text("13. İnsan resursları departamentinə tapşırılsın ki, əmrdən irəli gələn məsələləri" +
                " həll etsin. ");
        Text text15 = new Text("14. Əmr imzalandığı gündən qüvvəyə minir.");
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
                Text text12 = new Text("11. Qeyd:  " + note);
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

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfPaidSocialVacation(Document document, Operation operation) {
        log.info("pdfPaidSocialBusinessTrip PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“İşçiyə qismən ödənişli sosial məzuniyyət verilməsi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("Azərbaycan Respublikası Əmək Məcəlləsinin 127-ci maddəsini rəhbər " +
                "tutaraq işçinin ərizəsinə əsasən,");
        paragraph2.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text0 = new Text("Aşağıda məlumatları qeyd olunan işçiyə qismən ödənişli sosial məzuniyyət verilsin.");
        Text text1 = new Text("1. İşçinin soyadı, adı, atasının adı: " + employee.getFullName()).setBold();
        Text text2 = new Text("2. Struktur bölmə: " + employee.getPosition().getDepartment()).setBold();
        Text text3 = new Text("3. Alt struktur bölmə: " + employee.getPosition().getSubDepartment()).setBold();
        Text text4 = new Text("4. Vəzifəsi: " + employee.getPosition().getVacancy().getName()).setBold();
        Text text5 = new Text("5. Məzuniyyət müddəti: " + operation.getDayInEvent()).setBold();
        Text text6 = new Text("6. Məzuniyyətə buraxılma tarixləri: " + operation.getEventFrom() + " / " +
                operation.getEventTo()).setBold();
        Text text7 = new Text("7. İşə başlama tarixi: " + operation.getJoinDate()).setBold();
        Text text8 = new Text("8. İnsan resursları və Maliyyə departamentlərinə tapşırılsın ki, əmrdən irəli" +
                " gələn zəruri məsələlərin həllini təmin etsinlər");
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
        log.info("********** pdfPaidSocialBusinessTrip PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfPaidDayOff(Document document, Operation operation) {
        log.info("pdfPaidDayOff PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Kollektiv müqaviləyə əsasən ödənişli istirahət günü barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("“Bakı Beynəlxalq Dəniz Ticarət Limanı” QSC və “Bakı Beynəlxalq " +
                "Dəniz Ticarət Limanı” QSC işçilərinin “Limançı” Həmkarlar İttifaqının komitəsi arasında bağlanmış " +
                "müqaviləni rəhbər tutaraq işçinin ərizəsinə əsasən,");
        paragraph2.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text0 = new Text("Aşağıda məlumatları qeyd olunan işçiyə ödənişli istirahət günü verilsin.");
        Text text1 = new Text("1. İşçinin soyadı, adı, atasının adı: " + employee.getFullName()).setBold();
        Text text2 = new Text("2. Struktur bölmə: " + employee.getPosition().getDepartment()).setBold();
        Text text3 = new Text("3. Alt struktur bölmə: " + employee.getPosition().getSubDepartment()).setBold();
        Text text4 = new Text("4. Vəzifəsi: " + employee.getPosition().getVacancy().getName()).setBold();
        Text text5 = new Text("5. Ödənişli istirahət müddəti: " + operation.getDayInEvent()).setBold();
        Text text6 = new Text("6. Ödənişli istirahətə buraxılma tarixləri: " + operation.getEventFrom() + " / " +
                operation.getEventTo()).setBold();
        Text text7 = new Text("7. İşə başlama tarixi: " + operation.getJoinDate()).setBold();
        Text text8 = new Text("8. Ödənişli istirahət verilməsinin səbəbi: ").setBold();
        String vacationReason = "";
        Text text9 = new Text("9. İnsan resursları və Maliyyə departamentlərinə tapşırılsın ki, əmrdən irəli gələn " +
                "zəruri məsələlərin həllini təmin etsinlər.");
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
        document.add(new Paragraph("8.1. " + vacationReason));
        document.add(new Paragraph(text8));
        document.add(new Paragraph(text9));
        document.add(new Paragraph(text10));
        log.info("********** pdfPaidDayOff PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfSocialVacation(Document document, Operation operation) {
        log.info("pdfSocialBusinessTrip PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“İşçiyə sosial məzuniyyət verilməsi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("Azərbaycan Respublikası Əmək Məcəlləsinin 125-ci maddəsinin " +
                "1-ci bəndi və SN " + operation.getSeries().getValue() + " seriyalı" + operation.getSerialNumber() +
                " saylı əmək qabiliyyətinin olmaması vərəqəsinə əsasən,");
        paragraph2.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text0 = new Text("Aşağıda məlumatları qeyd olunan işçiyə ödənişli sosial məzuniyyət verilsin.");
        Text text1 = new Text("1. İşçinin soyadı, adı, atasının adı: " + employee.getFullName()).setBold();
        Text text2 = new Text("2. Struktur bölmə: " + employee.getPosition().getDepartment()).setBold();
        Text text3 = new Text("3. Alt struktur bölmə: " + employee.getPosition().getSubDepartment()).setBold();
        Text text4 = new Text("4. Vəzifəsi: " + employee.getPosition().getVacancy().getName()).setBold();
        Text text5 = new Text("5. Məzuniyyət müddəti: " + operation.getDayInEvent()).setBold();
        Text text6 = new Text("6. Məzuniyyətə buraxılma tarixləri: " + operation.getEventFrom() + " / " +
                operation.getEventTo()).setBold();
        Text text7 = new Text("7. İşə başlama tarixi: " + operation.getJoinDate()).setBold();
        Text text8 = new Text("8. İnsan resursları və Maliyyə departamentlərinə tapşırılsın ki, əmrdən irəli gələn " +
                "zəruri məsələlərin həllini təmin etsinlər. ");
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
        log.info("********** pdfSocialBusinessTrip PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfIncreaseVacation(Document document, Operation operation) {
        log.info("pdfSocialBusinessTrip PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Əmək məzuniyyətinin uzadılması barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph2 = new Paragraph("Azərbaycan Respublikası Əmək Məcəlləsinin 134-cü maddəsinin " +
                "3-cü hissəsinin “a” bəndini rəhbər tutaraq işçinin ərizəsi və əmək qabiliyyətinin olmaması " +
                "vərəqəsinə əsasən, ");
        paragraph2.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text0 = new Text("Aşağıda məlumatları qeyd olunan işçinin əmək məzuniyyəti uzadılsın.");
        Text text1 = new Text("1. İşçinin soyadı, adı, atasının adı: " + employee.getFullName()).setBold();
        Text text2 = new Text("2. Struktur bölmə: " + employee.getPosition().getDepartment()).setBold();
        Text text3 = new Text("3. Alt struktur bölmə: " + employee.getPosition().getSubDepartment()).setBold();
        Text text4 = new Text("4. Vəzifəsi: " + employee.getPosition().getVacancy().getName()).setBold();
        Text text5 = new Text("5. Məzuniyyətə buraxıldığı müddət: " + operation.getDayInEvent()).setBold();
        Text text6 = new Text("6. Məzuniyyətə buraxılma tarixləri: " + operation.getEventFrom() + " / " +
                operation.getEventTo()).setBold();
        Text text7 = new Text("7. Məzuniyyət dövrünə təsadüf edən əmək qabiliyyəti olmama günləri: ").setBold();
        Text text8 = new Text("8. Məzuniyyətin keçirildiyi tarixlər: " + operation.getEventFrom2() + " / " +
                operation.getEventTo2()).setBold();
        Text text9 = new Text("9. Məzuniyyətin keçirildiyi müddət: " + operation.getDayInEvent2()).setBold();
        Text text10 = new Text("10. İşə başlama tarix: " + operation.getJoinDate()).setBold();
        Text text11 = new Text("11. Maliyyə departamentinə tapşırılsın ki, əmrdən irəli gələn ödəmə məsələlərini " +
                "həll etsin. ");
        Text text12 = new Text("Baş direktor                                                                   Taleh " +
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
        document.add(new Paragraph(text11));
        document.add(new Paragraph(text12));
        log.info("********** pdfSocialBusinessTrip PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfIncreaseBusinessTrip(Document document, Operation operation) {
        log.info("pdfIncreaseBusinessTrip PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Ezamiyyənin müddətinin uzadılması barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph(operation.getMainOfOrder());

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text1 = new Text("Aşağıda məlumatları qeyd olunan işçi(lərin)nin ezam müddəti uzadılsın.");
        Text text2 = new Text("1. İşçi (lər)nin soyadı, adı, atasının adı " + employee.getFullName());
        Text text3 = new Text("2. İşlədiyi struktur bölmə: " + employee.getPosition().getDepartment().getName());
        Text text4 = new Text("3. İşlədiyi alt struktur bölmə: " + employee.getPosition().getSubDepartment().getName());
        Text text5 = new Text("4. Vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text6 = new Text("5. Ezam olunduğu ölkə/şəhər/rayon: " + operation.getBusinessTripLocation());
        Text text7 = new Text("6. Ezam olunma tarixi: " + operation.getEventToBusinessTripDate() + " / " +
                operation.getEventFromBusinessTripDate());
        Text text8 = new Text("7. Ezamiyyət müddəti: " + operation.getBusinessTripTerm());
        Text text9 = new Text("8. Ezamiyyə müddətində işçinin yolda keçirdiyi istirahət gününə təsadüf etdiyi tarix: " +
                operation.getNonWorkDay());
        Text text10 = new Text("9. Ezamiyyə müddətində yolda keçirilmiş istirahət gününün əvəzinə verilmiş " +
                "istirahət günü: " + operation.getGivenNonWorkDay());
        Text text11 = new Text("10. İşçinin işə başlama tarixi" + operation.getJoinDate());
        Text text13 = new Text("12. Maliyyə departamentinə tapşırılsın ki, Qanunvericiliyə uyğun olaraq ezamiyyə" +
                " xərclərinin ödənilməsini təmin etsin.");
        Text text14 = new Text("13. İnsan resursları departamentinə tapşırılsın ki, əmrdən irəli gələn məsələləri" +
                " həll etsin. ");
        Text text15 = new Text("14. Əmr imzalandığı gündən qüvvəyə minir.");
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
                Text text12 = new Text("11. Qeyd:  " + note);
                document.add(new Paragraph(text12));
            }
        }
        document.add(new Paragraph(text13));
        document.add(new Paragraph(text14));
        document.add(new Paragraph(text15));
        document.add(new Paragraph(text16));
        log.info("********** pdfIncreaseBusinessTrip PDF creator completed with operationId : {} **********",
                operation.getId());

    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfGiveVacation(Document document, Operation operation) {
        log.info("pdfGiveVacation PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“İşçiyə əmək məzuniyyəti verilməsi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph2 = new Paragraph("İşçinin ərizəsinə əsasən, ");
        paragraph2.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text1 = new Text("Aşağıda məlumatları qeyd olunan işçiyə əmək məzuniyyəti verilsin.");
        Text text2 = new Text("1. İşçi (lər)nin soyadı, adı, atasının adı " + employee.getFullName()).setBold();
        Text text3 = new Text("2. Struktur bölmə: " + employee.getPosition().getDepartment().getName()).setBold();
        Text text4 = new Text("3. Alt struktur bölmə: " +
                employee.getPosition().getSubDepartment().getName()).setBold();
        Text text5 = new Text("4. Vəzifəsi: " + employee.getPosition().getVacancy().getName()).setBold();
        Text text6 = new Text("5. Məzuniyyətin iş ili dövrü (dövrləri): ").setBold();
        Text text7 = new Text("6. Məzuniyyət müddəti: " + operation.getDayInEvent()).setBold();
        String vacationTime = "";
        Text text8 = new Text("7. Məzuniyyətin cəmi müddəti: ").setBold();
        Text text9 = new Text("8. Məzuniyyətə buraxılma tarixləri: " + operation.getEventFrom() + " / " +
                operation.getEventTo()).setBold();
        Text text10 = new Text("9. Məzuniyyət dövrünə təsadüf edən iş günü hesab olunmayan bayram günü(ləri) " +
                "və hüzn günü: ").setBold();
        Text text11 = new Text("10. İşə başlama tarixi" + operation.getJoinDate()).setBold();
        Text text13 = new Text("11. Maliyyə departamentinə tapşırılsın ki, əmrdən irəli gələn ödəmə məsələlərini " +
                "həll etsin.");
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
        document.add(new Paragraph("6.1. " + vacationTime));
        document.add(new Paragraph(text8));
        document.add(new Paragraph(text9));
        document.add(new Paragraph(text10));
        document.add(new Paragraph(text11));
        document.add(new Paragraph(text13));
        document.add(new Paragraph(text14));
        log.info("********** pdfGiveVacation PDF creator completed with operationId : {} **********",
                operation.getId());

    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfCallBackFromBusinessTrip(Document document, Operation operation) {
        log.info("pdfCallBackFromBusinessTrip PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“İşçinin/işçilərin ezamiyyətdən geri çağırılması barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph(operation.getMainOfOrder());

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text1 = new Text("Aşağıda məlumatları qeyd olunan işçi(lər) ezam edilsin.");
        Text text2 = new Text("1. İşçi (lər)nin soyadı, adı, atasının adı " + employee.getFullName());
        Text text3 = new Text("2. İşlədiyi struktur bölmə: " + employee.getPosition().getDepartment().getName());
        Text text4 = new Text("3. İşlədiyi alt struktur bölmə: " + employee.getPosition().getSubDepartment().getName());
        Text text5 = new Text("4. Vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text6 = new Text("5. Ezam olunduğu ölkə/şəhər/rayon: " + operation.getBusinessTripLocation());
        Text text7 = new Text("6. Ezam olunma tarixi: " + operation.getEventFromBusinessTripDate() + " / " +
                operation.getEventToBusinessTripDate());
        Text text8 = new Text("7. Ezamiyyət müddəti: " + operation.getBusinessTripTerm());
        Text text9 = new Text("8. Ezamiyyətdən geri çağırıldığı və işə başladığı tarix: " +
                operation.getCallBackDate());
        Text text11 = new Text("10. Maliyyə və İnsan resursları departamentinə tapşırılsın ki, əmrdən irəli gələn " +
                "məsələləri həll etsinlər.");
        Text text13 = new Text("11. Əmr imzalandığı gündən qüvvəyə minir.");
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
        if (operation.getNotes() != null) {
            for (String note : operation.getNotes()) {
                Text text12 = new Text("9. Qeyd:  " + note);
                document.add(new Paragraph(text12));
            }
        }
        document.add(new Paragraph(text11));
        document.add(new Paragraph(text13));
        document.add(new Paragraph(text14));
        log.info("********** pdfCallBackFromBusinessTrip PDF creator completed with operationId : {} **********",
                operation.getId());

    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfDecreaseWorkHours(Document document, Operation operation) {
        log.info("pdfDecreaseWorkHours PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“İş saatlarının qısaldılması barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("İşçinin əmək haqqı saxlanılmaqla iş vaxtı aşağıda qeyd edildiyi " +
                "kimi müəyyən edilsin.");
        paragraph2.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text1 = new Text("İşçinin əmək haqqı saxlanılmaqla iş vaxtı aşağıda qeyd edildiyi kimi müəyyən edilsin.");
        Text text2 = new Text("1. İşçinin soyadı, adı, ata adı: " + employee.getFullName());
        Text text3 = new Text("2. İşçinin işlədiyi struktur bölmə: " +
                employee.getPosition().getDepartment().getName());
        Text text4 = new Text("3. İşçinin işlədiyi alt struktur bölmə: " +
                employee.getPosition().getSubDepartment().getName());
        Text text5 = new Text("4. İşçinin vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text6 = new Text("5. İşçinin faktiki iş vaxtı: ");
        Text text7 = new Text("6. İşçinin keçirildiyi iş vaxtı: ");
        Text text8 = new Text("7. İnsan resursları və Maliyyə departamentlərinə tapşırılsın ki, əmrdən irəli gələn " +
                "zəruri məsələlərin həllini təmin etsinlər.");
        Text text9 = new Text("8. Əmr imzalandığı gündən qüvvəyə minir.");
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
        log.info("********** pdfDecreaseWorkHours PDF creator completed with operationId : {} **********",
                operation.getId());

    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfEducationVacation(Document document, Operation operation) {
        log.info("pdfEducationVacation PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Təhsil məzuniyyəti/Yaradıcılıq məzuniyyəti barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("İşçinin ərizəsinə və təhsil müəssisəsi tərəfindən verilən çağırış " +
                "arayışına əsasən,");
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text0 = new Text("Aşağıda məlumatları qeyd olunan işçi təhsil" +
                " (və ya yaradıcılıq) məzuniyyətinə buraxılsın. ");
        Text text1 = new Text("1. İşçinin soyadı, adı, atasının adı:  " +
                employee.getFullName());
        Text text2 = new Text("2. İşlədiyi struktur bölmənin adı:  " + employee.getPosition()
                .getDepartment().getName());
        Text text3 = new Text("3. İşçinin işlədiyi alt struktur bölmə:  " + employee.getPosition()
                .getSubDepartment().getName());
        Text text4 = new Text("4. İşlədiyi vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text5 = new Text("5. Təhsil (və ya yaradıcılıq) məzuniyyətinə buraxılma tarixləri " +
                operation.getEventFrom() + " / " + operation.getEventTo());
        Text text6 = new Text("6. Təhsil (yaradıcılıq) məzuniyyətinin müddəti: " + operation.getDayInEvent());
        Text text7 = new Text("7. İşə başlama tarixi: " + operation.getJoinDate());
        Text text8 = new Text("8. İnsan resursları və Maliyyə departamentlərinə tapşırılsın ki, əmrdən irəli gələn" +
                " məsələlərin həllini təmin etsinlər. ");
        Text text9 = new Text("9. İnsan Resursları və Maliyyə Departamentlərinə tapşırılsın ki, əmrdən irəli " +
                "gələn məsələlərin həllini təmin etsin.");
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
        log.info("********** pdfEducationVacation PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfMilitaryCalls(Document document, Operation operation) {
        log.info("pdfMilitaryCalls PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Hərbi çağırış barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph2 = new Paragraph("“Azərbaycan Respublikasında qismən səfərbərlik elan " +
                "edilməsi haqqında” Azərbaycan Respublikası Prezidentinin 28 sentyabr 2020-ci il tarixli Sərəncamını " +
                "rəhbər tutaraq, Azərbaycan Respublikası Əmək Məcəlləsinin 179-cu maddəsinin “g” və “ğ” bəndlərinə " +
                "əsasən," + operation.getMainOfOrder());
        paragraph2.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text0 = new Text("1. Aşağıda məlumatları qeyd olunan işçinin iş yeri və aylıq əmək haqqı saxlanılmaqla" +
                " müvəqqəti azad edilsin.");
        Text text1 = new Text("İşçinin soyadı, adı, atasının adı:  " + employee.getFullName());
        Text text2 = new Text("İşlədiyi struktur bölmə:  " + employee.getPosition()
                .getDepartment().getName());
        Text text3 = new Text("İşlədiyi alt struktur bölmə:  " + employee.getPosition()
                .getSubDepartment().getName());
        Text text4 = new Text("Vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text5 = new Text("Müvəqqəti azad olunma müddəti: " + operation.getDayInEvent());
        Text text6 = new Text("2. İnsan resursları və Maliyyə departamentinə tapşırılsın ki, əmrdən irəli gələn" +
                " zəruri məsələlərin həllini təmin etsin.");
        Text text7 = new Text("3. Əmr imzalandığı gündən qüvvəyə minir.");
        Text text8 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list
                .add(new ListItem(text1.getText()))
                .add(new ListItem(text2.getText()))
                .add(new ListItem(text3.getText()))
                .add(new ListItem(text4.getText()))
                .add(new ListItem(text5.getText()))
                .setMarginLeft(5);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text0));
        document.add(list);
        document.add(new Paragraph(text6));
        document.add(new Paragraph(text7));
        document.add(new Paragraph(text8));
        log.info("********** pdfMilitaryCalls PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfAdditionalDayOff(Document document, Operation operation) {
        log.info("pdfAdditionalDayOff PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“İşçiyə əlavə istirahət günü verilməsi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("Azərbaycan Respublikası Əmək Məcəlləsinin 104-cu maddəsinin " +
                "3-cü bəndinə və işçinin ərizəsinə əsasən " + operation.getMainOfOrder());
        paragraph2.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text0 = new Text("Aşağıda məlumatları qeyd olunan işçiyə əlavə istirahət günü verilsin.");
        Text text1 = new Text("1. İşçinin soyadı, adı, atasının adı: " + employee.getFullName());
        Text text2 = new Text("2. İşlədiyi struktur bölmə:  " + employee.getPosition()
                .getDepartment().getName());
        Text text3 = new Text("3. İşlədiyi alt struktur bölmə:  " + employee.getPosition()
                .getSubDepartment().getName());
        Text text4 = new Text("4. Vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text5 = new Text("5. İstirahət tarixi: " + operation.getEventFrom() + " / " + operation.getEventTo());
        Text text6 = new Text("6. İstirahətin müddəti: " + operation.getDayInEvent());
        Text text7 = new Text("7. İşçinin işə başlama tarixi: " + operation.getJoinDate());
        Text text8 = new Text("8. Maliyyə departamentinə tapşırılsın ki, işçinin aylıq əmək haqqı saxlanılsın.");
        Text text9 = new Text("9. İnsan resursları departamentinə tapşırılsın ki, əmrdən irəli gələn məsələləri " +
                "həll etsin.");
        Text text10 = new Text("10. Əmr imzalandığı gündən qüvvəyə minir.");
        Text text11 = new Text("Baş direktor                                                                   Taleh " +
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
        document.add(new Paragraph(text11));
        log.info("********** pdfAdditionalDayOff PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfSetAllowance(Document document, Operation operation) {
        log.info("pdfSetAllowance PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“İstehsalat qəzası zamanı xəsarət alan işçiyə müavinətin " +
                "təyin olunması barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph(operation.getMainOfOrder());

        Paragraph paragraph3 = new Paragraph("AR Nazirlər Kabinetinin 09 yanvar 2003-cü il tarixli " +
                "03 nömrəli qərarı ilə təsdiq olunmuş istehsalat qəzası və yaxud peşə xəstəliyi nəticəsində " +
                "sağlamlığı pozulmuş işçiyə və ya bu səbəbdən həlak olmuş işçinin ailə üzvlərinə ödənclərin" +
                " verilməsi qaydalarının tələblərinə müvafiq olaraq, ");
        paragraph2.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph4 = new Paragraph("ƏMR EDİRƏM:");
        paragraph4.setTextAlignment(TextAlignment.CENTER);
        paragraph4.setCharacterSpacing(10);
        paragraph4.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text0 = new Text("1. Aşağıda adı qeyd olunan işçiyə aylıq ödənc verilsin.");
        Text text1 = new Text(" İşçinin soyadı, adı, atasının adı: " + employee.getFullName());
        Text text2 = new Text(" Struktur bölmə:  " + employee.getPosition()
                .getDepartment().getName());
        Text text3 = new Text(" İşçinin vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text4 = new Text(" Ödənilmə başlanılan tarix: " + operation.getEventFrom() + " / " +
                operation.getEventTo());
        Text text5 = new Text(" Ödəncin məbləği: " + operation.getAmount());
        Text text6 = new Text("2. İnsan resursları və Maliyyə departamentlərinə tapşırılsın ki, əmrdən irəli gələn" +
                " məsələlərin həllini təmin etsinlər.");
        Text text7 = new Text("3. Zərərə görə aylıq ödənclər müəssisələrdə əmək haqqının kütləvi artımı zamanı " +
                "Qanunvericiliyə uyğun təshih olunsun.");
        Text text8 = new Text("Əsas: " + operation.getReason());
        Text text9 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list
                .add(new ListItem(text1.getText()))
                .add(new ListItem(text2.getText()))
                .add(new ListItem(text3.getText()))
                .add(new ListItem(text4.getText()))
                .add(new ListItem(text5.getText()))
                .setMarginLeft(5);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(paragraph4);
        document.add(new Paragraph(text0));
        document.add(list);
        document.add(new Paragraph(text6));
        document.add(new Paragraph(text7));
        document.add(new Paragraph(text8));
        document.add(new Paragraph(text9));
        log.info("********** pdfSetAllowance PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfCallBackFromWorkingVacation(Document document, Operation operation) {
        log.info("pdfCallBackFromWorkingVacation PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“Əmək məzuniyyətindən geri çağırılma barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("İşçinin ərizəsinə əsasən, ");
        paragraph2.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph4 = new Paragraph("ƏMR EDİRƏM:");
        paragraph4.setTextAlignment(TextAlignment.CENTER);
        paragraph4.setCharacterSpacing(10);
        paragraph4.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text0 = new Text("Aşağıda adı qeyd olunan işçi əmək məzuniyyətindən geri çağırılsın.");
        Text text1 = new Text("1. Geri çağırılan işçinin soyadı, adı, atasının adı:  " + employee.getFullName());
        Text text2 = new Text("2. İşlədiyi struktur bölmə: " + employee.getPosition().getDepartment().getName());
        Text text3 = new Text("3. İşlədiyi alt struktur bölmə: " + employee.getPosition().getSubDepartment().getName());
        Text text4 = new Text("4. Vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text5 = new Text("5. Məzuniyyətə buraxıldığı tarixləri: " + operation.getEventFrom() + " / " +
                operation.getEventTo());
        Text text6 = new Text("6. Geri çağırılma tarixi: " + operation.getCallBackDate());
        Text text7 = new Text("7. Geri çağırılma səbəbi: " + operation.getReason());
        Text text8 = new Text("8. Əlavə qeydlər: ");
        Text text9 = new Text("1. İnsan resursları və Maliyyə departamentlərinə tapşırılsın ki, əmrdən irəli " +
                "gələn məsələlərin həllini təmin etsin.");
        Text text10 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list
                .add(new ListItem(text10.getText()))
                .setMarginLeft(5);
        document.add(paragraph1);
        document.add(paragraph2);

        document.add(paragraph4);
        document.add(new Paragraph(text0));
        document.add(new Paragraph(text1));
        document.add(new Paragraph(text2));
        document.add(new Paragraph(text3));
        document.add(new Paragraph(text4));
        document.add(new Paragraph(text5));
        document.add(new Paragraph(text6));
        document.add(new Paragraph(text7));
        document.add(new Paragraph(text8));
        document.add(list);
        document.add(new Paragraph(text9));
        document.add(new Paragraph(text10));
        log.info("********** pdfCallBackFromWorkingVacation PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfPaymentOfCompensation(Document document, Operation operation) {
        log.info("pdfPaymentOfCompensation PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);
        Paragraph paragraph1 = new Paragraph("“İstifadə edilməmiş əmək məzuniyyətinə görə kompensasiya " +
                "ödənilməsi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph(
                "Azərbaycan Respublikası Əmək Məcəlləsinin 135-ci maddəsinin 2-ci bəndinə və işçinin " +
                        "ərizəsinə əsasən, ");
        paragraph2.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);

        Employee employee = operation.getEmployee();
        Text text0 = new Text("1. Qanunvericiliyin tələbinə uyğun olaraq aşağıda məlumatları qeyd olunan işçiyə " +
                "müvafiq iş ilində müəyyən səbəbdən istifadə edilməmiş əmək məzuniyyətinə görə müəyyən olunmuş " +
                "qaydada və məbləğdə kompensasiya ödənilsin.  ");
        Text text1 = new Text(" İşçinin soyadı, adı, atasının adı:  " + employee.getFullName());
        Text text2 = new Text(" İşlədiyi struktur bölmə: " + employee.getPosition().getDepartment().getName());
        Text text3 = new Text(" İşlədiyi alt struktur bölmə: " + employee.getPosition().getSubDepartment().getName());
        Text text4 = new Text(" Vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text5 = new Text(" İstifadə etmədiyi məzuniyyətin iş ili dövrü/ dövrləri: ");
        //??
        // operation.getEventFrom() + " / " + operation.getEventTo());
        Text text6 = new Text(" İstifadə edilməmiş məzuniyyət günləri: ");
        Text text7 = new Text(" Əsas məzuniyyət günləri");
        Text text8 = new Text(" Staja görə əlavə məzuniyyət günləri");
        Text text9 = new Text(" Əmək şəraitinə görə əlavə məzuniyyət günləri");
        Text text10 = new Text(" Uşaqlı qadınlara verilən əlavə məzuniyyət günləri");
        Text text11 = new Text("2. İnsan resursları və Maliyyə departamentlərinə tapşırılsın ki, əmrdən irəli gələn" +
                " məsələlərin həllini təmin etsinlər.");
        Text text12 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list
                .add(new ListItem(text7.getText()))
                .add(new ListItem(text8.getText()))
                .add(new ListItem(text9.getText()))
                .add(new ListItem(text10.getText()))
                .setMarginLeft(5);

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
        document.add(list);
        document.add(new Paragraph(text11));
        document.add(new Paragraph(text12));
        log.info("********** pdfPaymentOfCompensation PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfAttractToWork(Document document, Operation operation) {
        log.info("pdfAttractToWork PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);

        Text text01 = new Text(operation.getReason());

        Paragraph paragraph2 = new Paragraph(operation.getMainOfOrder());
        paragraph2.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Employee employee = operation.getEmployee();
        Text text0 = new Text("1. İşə cəlb edilən işçinin və ya işçilərin soyadı, adı, atasının adı: " +
                employee.getFullName());
        Text text1 = new Text("2. İşə cəlb edilən işçi və ya işçilərin işlədiyi struktur bölmə: " +
                employee.getPosition().getDepartment().getName());
        Text text2 = new Text("3. İşə cəlb edilən işçi və ya işçilərin vəzifəsi: " +
                employee.getPosition().getSubDepartment().getName());
        Text text3 = new Text("4. İşə cəlb edilmə tarixi: " + employee.getPosition().getVacancy().getName());
        Text text4 = new Text("5. İşə cəlb edilmə saatları: ");
        Text text5 = new Text("6. Maliyyə Departamentinə tapşırılsın ki, " + employee.getFullName() +
                " Azərbaycan Respublikası Əmək Məcəlləsinin 164-cü maddəsinin 1-ci hissəsinə əsasən əmək haqqı " +
                "ikiqat məbləğdə ödənilsin.");
        Text text6 = new Text("Əsas:  Struktur bölmə rəhbərinin təqdimatı və " + employee.getFullName() + " ərizəsi");
        Text text7 = new Text("Struktur bölmə rəhbərinin soyadı, adı, atasının adı: ");
        Text text8 = new Text("Struktur bölmənin adı: ");
        Text text9 = new Text("Vəzifəsi: ");
        Text text10 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        List list2 = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list2
                .add(new ListItem(text7.getText()))
                .add(new ListItem(text8.getText()))
                .add(new ListItem(text9.getText()));

        document.add(new Paragraph(text01));
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text0));
        document.add(new Paragraph(text1));
        document.add(new Paragraph(text2));
        document.add(new Paragraph(text3));
        document.add(new Paragraph(text4));
        document.add(new Paragraph(text5));
        document.add(new Paragraph(text6));
        document.add(list2);
        document.add(new Paragraph(text10));
        log.info("********** pdfAttractToWork PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfFiredFromWork(Document document, Operation operation) {
        log.info("pdfFiredFromWork PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);

        Paragraph paragraph1 = new Paragraph("“İşçinin işdən kənarlaşdırılması barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph(operation.getMainOfOrder());
        paragraph2.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);

        Employee employee = operation.getEmployee();
        Text text0 = new Text("1. Aşağıda məlumatları qeyd olunan işçi işdən kənarlaşdırılsın. ");
        Text text1 = new Text("1.1. İşçinin soyadı, adı, ata adı: " + employee.getFullName());
        Text text2 = new Text("1.2. İşlədiyi struktur bölmə: " + employee.getPosition().getDepartment().getName());
        Text text3 = new Text("1.3. İşlədiyi alt struktur bölmə: " +
                employee.getPosition().getSubDepartment().getName());
        Text text4 = new Text("1.4. Vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text5 = new Text("1.5. Kənarlaşdırma müddəti: " + operation.getDayInEvent());
        Text text6 = new Text("2. Maliyyə departamentinə tapşırılsın ki, Azərbaycan Respublikası Əmək Məcəlləsinin " +
                "62-ci maddəsinə uyğun olaraq, həmin işçiyə işdən kənar edilən vaxt ərzində əmək haqqı ödənilməsin.");
        Text text7 = new Text("3. İnsan resursları departamentinə tapşırılsın ki, bu əmrdən irəli gələn zəruri" +
                " məsələlərin həllini təmin etsin.");
        Text text8 = new Text("4. Əmrin icrasına nəzarət Baş direktorun müavini Söhrab İsmayılova həvalə edilsin.");
        Text text9 = new Text("5. Bu əmr imzalandığı tarixdən qüvvəyə minir.");
        Text text10 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov").setFont(bold);

        List list1 = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        list1
                .add(new ListItem(text1.getText()))
                .add(new ListItem(text2.getText()))
                .add(new ListItem(text3.getText()))
                .add(new ListItem(text4.getText()))
                .add(new ListItem(text5.getText()))
                .setMarginLeft(5);

        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(new Paragraph(text0));
        document.add(list1);
        document.add(new Paragraph(text6));
        document.add(new Paragraph(text7));
        document.add(new Paragraph(text8));
        document.add(new Paragraph(text9));
        document.add(new Paragraph(text10));
        log.info("********** pdfFiredFromWork PDF creator completed with operationId : {} **********",
                operation.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfFreeVacation(Document document, Operation operation) {
        log.info("pdfFiredFromWork PDF creator started with operationId : {}", operation.getId());
        document.setFont(regular);

        Paragraph paragraph1 = new Paragraph("“İşçiyə ödənişsiz məzuniyyət verilməsi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph2 = new Paragraph("Azərbaycan Respublikası Əmək Məcəlləsinin " +
                "129 və 130-cu maddələrini rəhbər tutaraq, işçinin ərizəsinə əsasən, ");
        paragraph2.setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);

        Employee employee = operation.getEmployee();
        Text text0 = new Text("Aşağıda məlumatları qeyd olunan işçiyə ödənişsiz məzuniyyət verilsin.");
        Text text1 = new Text("1. İşçinin soyadı, adı, ata adı: " + employee.getFullName());
        Text text2 = new Text("2. Struktur bölmə: " + employee.getPosition().getDepartment().getName());
        Text text3 = new Text("3. Alt struktur bölmə: " + employee.getPosition().getSubDepartment().getName());
        Text text4 = new Text("4. Vəzifəsi: " + employee.getPosition().getVacancy().getName());
        Text text5 = new Text("5. Məzuniyyət müddəti: " + operation.getDayInEvent());
        Text text6 = new Text("6. Məzuniyyətə buraxılma tarixləri: " +
                operation.getEventFrom() + " / " + operation.getEventTo());
        Text text7 = new Text("7. İşə başlama tarixi: " + operation.getJoinDate());
        Text text8 = new Text("8. İnsan resursları və Maliyyə departamentlərinə tapşırılsın ki, əmrdən irəli gələn " +
                "zəruri məsələlərin həllini təmin etsinlər.");
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
        log.info("********** pdfFiredFromWork PDF creator completed with operationId : {} **********",
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

    private String getBetweenDates(LocalDate from, LocalDate to) {
        long dayDiff = ChronoUnit.DAYS.between(from, to);
        String result = "";
        if (dayDiff > 365) {
            long temp = dayDiff % 365;
            result += (dayDiff - temp) / 365 + " il ";
            dayDiff = temp;
        }
        if (dayDiff > 30) {
            long temp = dayDiff % 30;
            result += (dayDiff - temp) / 30 + " ay ";
            dayDiff = temp;
        }
        if (dayDiff > 0) {
            result += dayDiff + " gün";
        }
        return result;
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
