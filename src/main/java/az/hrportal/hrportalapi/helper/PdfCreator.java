package az.hrportal.hrportalapi.helper;

import az.hrportal.hrportalapi.constant.DocumentType;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.operation.Operation;
import az.hrportal.hrportalapi.domain.position.Position;
import az.hrportal.hrportalapi.dto.DocumentData;
import az.hrportal.hrportalapi.dto.position.response.GeneralInfoResponseDto;
import az.hrportal.hrportalapi.error.exception.DocumentException;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.mapper.position.PositionResponseMapper;
import az.hrportal.hrportalapi.repository.OperationRepository;
import az.hrportal.hrportalapi.repository.employee.EmployeeRepository;
import az.hrportal.hrportalapi.repository.position.DepartmentRepository;
import az.hrportal.hrportalapi.repository.position.InstitutionRepository;
import az.hrportal.hrportalapi.repository.position.PositionRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Slf4j
@Component
public class PdfCreator {
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final PositionResponseMapper positionResponseMapper;
    private final OperationRepository operationRepository;
    private final DepartmentRepository departmentRepository;
    private final InstitutionRepository institutionRepository;

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfCreatePosition(Document document, DocumentData documentData, PdfFont bold) {
        log.info("createPosition PDF creator started with {}", documentData);
        Paragraph paragraph1 = new Paragraph("“Ştat vahid (lər) inin təsis edilməsi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("Əmrin əsası: " + documentData.getMain());
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Position position = positionRepository.findById(documentData.getPositionId()).orElseThrow(() ->
                new EntityNotFoundException(Position.class, documentData.getPositionId()));
        GeneralInfoResponseDto translatedToAze = positionResponseMapper.toGeneralInfoResponseDto(position);
        if (translatedToAze == null) {
            translatedToAze = new GeneralInfoResponseDto();
        }
        Text text1 = new Text("1. Aşağıda qeyd olunan Cəmiyyətin struktur bölməsində qeyd olunan əmək haqqı ilə ştat" +
                " vahidi vahidləri təsis edilsin.");
        Text subText1 = new Text("Ştat cədvəli dəyişiklik edilən struktur bölmə: " +
                translatedToAze.getDepartmentName());
        Text subText2 = new Text("Tabe struktur bölmənin adı: ");
        Text subText3 = new Text("Ştat vahidinin adı (vəzifə): " + translatedToAze.getVacancyName());
        Text subText4 = new Text("Ştat vahidi (say):   " + translatedToAze.getVacancyCount());
        Text subText5 = new Text("Əmək haqqı AZN(vergilər və digər ödənişlər daxil olmaqla): " +
                translatedToAze.getSalary());
        Text subText6 = new Text("İş rejimi: " + translatedToAze.getWorkMode());
        Text subText7 = new Text("Təsis edilən vəzifənin kateqoriyası: " + translatedToAze.getVacancyCategory());
        Text subText8 = new Text("İş yerinin ünvanı: " + translatedToAze.getWorkPlace());
        Text text2 = new Text("2. Maliyyə və İnsan resursları departamentinə tapşırılsın ki, " +
                "mrdən irəli gələn zəruri məsələlərin həllini təmin etsinlər.");
        Text text3 = new Text("3. Əmrin icrasına nəzarət Baş direktorun müavini Söhrab İsmayılova həvalə edilsin. ");
        Text text4 = new Text("4. Əmr imzalandığı gündən qüvvəyə minir. ");
        Text text5 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov");
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
        Operation saved = save(position, documentData);
        log.info("********** createPosition PDF creator completed with operationId : {} **********", saved.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfDeletePosition(Document document, DocumentData documentData, PdfFont bold) {
        log.info("deletePosition PDF creator started with {}", documentData);
        Paragraph paragraph1 = new Paragraph("“Ştat vahid(lər)inin ləğv edilməsi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("Əmrin əsası: " + documentData.getMain());
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Position position = positionRepository.findById(documentData.getPositionId()).orElseThrow(() ->
                new EntityNotFoundException(Position.class, documentData.getPositionId()));
        GeneralInfoResponseDto translatedToAze = positionResponseMapper.toGeneralInfoResponseDto(position);
        if (translatedToAze == null) {
            translatedToAze = new GeneralInfoResponseDto();
        }
        Text text1 = new Text("1. Aşağıda qeyd olunan Cəmiyyətin struktur bölməsində qeyd olunan ştat vahidi (ləri)" +
                " ləğv edilsin. ");
        Text subText1 = new Text("Ştat cədvəli dəyişiklik edilən struktur bölmə: " +
                translatedToAze.getDepartmentName());
        Text subText2 = new Text("Tabe struktur bölmənin adı: ");
        Text subText3 = new Text("Ştat vahidinin adı (vəzifə): " + translatedToAze.getVacancyName());
        Text subText4 = new Text("Ştat vahidi (say):   " + translatedToAze.getVacancyCount());
        Text subText5 = new Text("Əmək haqqı AZN(vergilər və digər ödənişlər daxil olmaqla): " +
                translatedToAze.getSalary());
        Text subText6 = new Text("İş rejimi: " + translatedToAze.getWorkMode());
        Text subText8 = new Text("İş yerinin ünvanı: " + translatedToAze.getWorkPlace());
        Text text2 = new Text("2. Maliyyə və İnsan resursları departamentinə tapşırılsın ki, " +
                "mrdən irəli gələn zəruri məsələlərin həllini təmin etsinlər.");
        Text text3 = new Text("3. Əmrin icrasına nəzarət Baş direktorun müavini Söhrab İsmayılova həvalə edilsin. ");
        Text text4 = new Text("4. Əmr imzalandığı gündən qüvvəyə minir. ");
        Text text5 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov");
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
        Operation saved = save(position, documentData);
        log.info("********** deletePosition PDF creator completed with operationId : {} **********", saved.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfChangeSalary(Document document, DocumentData documentData, PdfFont bold) {
        log.info("pdfChangeSalary PDF creator started with {}", documentData);

        Paragraph paragraph1 = new Paragraph("“Ştat əmək haqqının dəyişdirilməsi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("Əmrin əsası: " + documentData.getMain());
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Position position = positionRepository.findById(documentData.getPositionId()).orElseThrow(() ->
                new EntityNotFoundException(Position.class, documentData.getPositionId()));
        GeneralInfoResponseDto translatedToAze = positionResponseMapper.toGeneralInfoResponseDto(position);
        if (translatedToAze == null) {
            translatedToAze = new GeneralInfoResponseDto();
        }
        Text text1 = new Text("1.Aşağıda qeyd olunan Cəmiyyətin struktur bölməsində qeyd olunan vəzifəsinin" +
                " əmək haqqı dəyişdirilsin.");
        Text subText1 = new Text("Ştat cədvəli dəyişiklik edilən struktur bölmə: " +
                translatedToAze.getDepartmentName());
        Text subText2 = new Text("Tabe struktur bölmənin adı: ");
        Text subText3 = new Text("Ştat vahidinin adı (vəzifə): " + translatedToAze.getVacancyName());
        Text subText4 = new Text("Əmək haqqı dəyişdirilən ştat vahidi (say): " + translatedToAze.getVacancyCount());
        Text subText5 = new Text("Ştatın faktiki əmək haqqı Azn(vergilər və digər ödənişlər daxil olmaqla):  " +
                translatedToAze.getSalary());
        Text subText6 = new Text("Dəyişiklik təklif edilmiş əmək haqqı:   " + documentData.getDemandedSalary());
        Text subText7 = new Text("İş yerinin ünvanı: " + translatedToAze.getWorkPlace());
        Text text2 = new Text("2. Maliyyə və İnsan resursları departamentinə tapşırılsın ki, " +
                "mrdən irəli gələn zəruri məsələlərin həllini təmin etsinlər.");
        Text text3 = new Text("3. Əmrin icrasına nəzarət Baş direktorun müavini Söhrab İsmayılova həvalə edilsin. ");
        Text text4 = new Text("4. Əmr imzalandığı gündən qüvvəyə minir. ");
        Text text5 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov");
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
        Operation saved = save(position, documentData);
        log.info("********** pdfChangeSalary PDF creator completed with operationId : {} **********", saved.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfEndJob(Document document, DocumentData documentData, PdfFont bold) {
        log.info("createEndJob PDF creator started with {}", documentData);
        Employee employee = employeeRepository.findById(documentData.getEmployeeId()).orElseThrow(() ->
                new EntityNotFoundException(Employee.class, documentData.getEmployeeId()));
        Paragraph paragraph1 = new Paragraph("“Əmək müqaviləsinə xitam verilməsi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("Əmrin əsası: " + documentData.getMain());
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Position position = employee.getPosition();
        GeneralInfoResponseDto translatedToAze = positionResponseMapper.toGeneralInfoResponseDto(position);
        if (translatedToAze == null) {
            translatedToAze = new GeneralInfoResponseDto();
        }
        Text text1 = new Text("1. İşçinin soyadı, adı, atasının adı:  " +
                employee.getFullName());
        Text text2 = new Text("2. İşlədiyi struktur bölmənin adı:  " + translatedToAze.getDepartmentName());
        Text text3 = new Text("3. İşlədiyi alt struktur bölmənin adı: " + translatedToAze.getSubDepartmentName());
        Text text4 = new Text("4. İşçinin vəzifəsi:  " + translatedToAze.getVacancyName());
        Text text5 = new Text("5. İşdən azad olma tarixi:  " + documentData.getDismissalDate());
        Text text6 = new Text("6. İşdən azad olma səbəbi:  " + documentData.getDismissalReason());

        Text text7 = new Text("7.İstifadə edilməmiş məzuniyyət \n" +
                "günlərinə görə kompensasiya:  " + "HESABLA");

        Text text9 = new Text("9. Maliyyə Departamentinə tapşırılsın ki, ödəniş məsələlərini həll etsin.");
        Text text10 = new Text("10. İnsan resursları departamentinə tapşırılsın ki, əmrlə işçi tanış edilsin. ");
        Text text11 = new Text("11. Əmr imzalandığı gündən qüvvəyə minir. ");
        Text text12 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov");

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
        if (documentData.getNote() != null) {
            Text text8 = new Text("8.Qeyd:  " + documentData.getNote());
            document.add(new Paragraph(text8));
        }
        document.add(new Paragraph(text9));
        document.add(new Paragraph(text10));
        document.add(new Paragraph(text11));
        document.add(new Paragraph(text12));
        Operation saved = save(employee, documentData);
        log.info("********** createEndJob PDF creator completed with operationId : {} **********", saved.getId());
    }

    @SuppressWarnings({"checkstyle:variabledeclarationusagedistance",
            "checkstyle:avoidescapedunicodecharacters"})
    protected void pdfCreateDepartment(Document document, DocumentData documentData, PdfFont bold) {
       /* log.info("pdfCreateDepartment PDF creator started with {}", documentData);
        Paragraph paragraph1 = new Paragraph("“Strukturun təsis edilməsi barədə”");
        paragraph1.setTextAlignment(TextAlignment.CENTER);
        paragraph1.setFont(bold);

        Paragraph paragraph2 = new Paragraph("Əmrin əsası: " + documentData.getMain());
        paragraph2.setTextAlignment(TextAlignment.CENTER);
        paragraph2.setFont(bold);

        Paragraph paragraph3 = new Paragraph("ƏMR EDİRƏM:");
        paragraph3.setTextAlignment(TextAlignment.CENTER);
        paragraph3.setCharacterSpacing(10);
        paragraph3.setFont(bold);

        Department department = departmentRepository.findByName(documentData.getDepartmentName()).orElseThrow(() ->
                new EntityNotFoundException(Department.class, documentData.getDepartmentName()));
        Text text1 = new Text("1.Cəmiyyətdə aşağıda qeyd olunan struktur bölmə təsis edilsin. ");
        Text subText2 = new Text("Struktur vahidinin adı:  " + department.getName());
        Text subText3 = new Text("Struktur bölmənin adı: " + department.get);
        Text subText4 = new Text("İş yerinin ünvanı:    " + translatedToAze.getVacancyCount());
        Text subText5 = new Text("•Strukturun bölmənin tabe olduğu kurator rəhbər:  " +
                translatedToAze.getSalary());
        Text text2 = new Text("2. Maliyyə və İnsan resursları departamentinə tapşırılsın ki, əmrdən irəli gələn" +
                " zəruri məsələlərin həllini təmin etsinlər.");
        Text text3 = new Text("3. Əmrin icrasına nəzarət Baş direktorun müavini Söhrab İsmayılova həvalə edilsin. ");
        Text text4 = new Text("4. Əmr imzalandığı gündən qüvvəyə minir. ");
        Text text5 = new Text("Baş direktor                                                                   Taleh " +
                "Ziyadov");
        text5.setFont(bold);

        List list = new List()
                .setSymbolIndent(12).setFont(bold)
                .setListSymbol("\u2022");

        if (documentData.getInstitutionName() != null) {
            Text subText1 = new Text("Müəssisənin adı: " + documentData.getInstitutionName());
            list
                    .add(new ListItem(subText1.getText()))
                    .setMarginLeft(5);
        }

        list
                .add(new ListItem(subText2.getText()))
                .add(new ListItem(subText3.getText()))
                .add(new ListItem(subText4.getText()))
                .add(new ListItem(subText5.getText()))
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
        Operation saved = save(position, documentData);
        log.info("********** pdfCreateDepartment PDF creator completed with operationId : {} **********",
                saved.getId());*/
    }

    @SuppressWarnings("checkstyle:localvariablename")
    protected PdfFont getTTInterphasesFont(boolean isBold) {
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

    @Transactional
    protected Operation save(Employee employee, DocumentData documentData) {
        Operation operation;
        if (documentData.getDocumentType() == 1)
            operation = Operation.builder()
                    .employee(employee)
                    .dismissalDate(LocalDate.parse(documentData.getDismissalDate(),
                            DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                    .dismissalReason(documentData.getDismissalReason())
                    .documentType(DocumentType.SHTAT_VAHIDININ_TESISI)
                    .build();
        else if (documentData.getDocumentType() == 2)
            operation = Operation.builder()
                    .employee(employee)
                    .dismissalDate(LocalDate.parse(documentData.getDismissalDate(),
                            DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                    .dismissalReason(documentData.getDismissalReason())
                    .documentType(DocumentType.SHTAT_VAHIDININ_LEGVI)
                    .build();
        else if (documentData.getDocumentType() == 8)
            operation = Operation.builder()
                    .employee(employee)
                    .dismissalDate(LocalDate.parse(documentData.getDismissalDate(),
                            DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                    .dismissalReason(documentData.getDismissalReason())
                    .documentType(DocumentType.XITAM)
                    .build();
        else
            operation = Operation.builder()
                    .employee(employee)
                    .dismissalDate(LocalDate.parse(documentData.getDismissalDate(),
                            DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                    .dismissalReason(documentData.getDismissalReason())
                    .documentType(DocumentType.SHTAT_VAHIDININ_TESISI)
                    .build();
        return operationRepository.save(operation);
    }

    @Transactional
    protected Operation save(Position position, DocumentData documentData) {
        Operation operation;
        if (documentData.getDocumentType() == 1)
            operation = Operation.builder()
                    .position(position)
                    .salary(position.getSalary().getSalary())
                    .documentType(DocumentType.SHTAT_VAHIDININ_TESISI)
                    .build();
        else
            operation = Operation.builder()
                    .position(position)
                    .dismissalDate(LocalDate.parse(documentData.getDismissalDate(),
                            DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                    .dismissalReason(documentData.getDismissalReason())
                    .documentType(DocumentType.SHTAT_VAHIDININ_TESISI)
                    .build();

        return operationRepository.save(operation);
    }
}
