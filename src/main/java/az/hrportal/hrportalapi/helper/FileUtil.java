package az.hrportal.hrportalapi.helper;

import az.hrportal.hrportalapi.constant.DocumentType;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.operation.Operation;
import az.hrportal.hrportalapi.domain.position.Position;
import az.hrportal.hrportalapi.dto.DocumentData;
import az.hrportal.hrportalapi.dto.position.response.GeneralInfoResponseDto;
import az.hrportal.hrportalapi.error.exception.DocumentException;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;
import az.hrportal.hrportalapi.error.exception.FileExtensionNotAllowedException;
import az.hrportal.hrportalapi.mapper.position.PositionResponseMapper;
import az.hrportal.hrportalapi.repository.OperationRepository;
import az.hrportal.hrportalapi.repository.employee.EmployeeRepository;
import az.hrportal.hrportalapi.repository.position.PositionRepository;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileUtil {
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final PositionResponseMapper positionResponseMapper;
    private final OperationRepository operationRepository;

    @Value("${file.upload.acceptable-extension}")
    private String[] acceptableExtensions;
    @Value("${file.upload.image-root-directory}")
    private String imageRootDirectory;
    private PdfFont regular;
    private PdfFont bold;

    @SneakyThrows
    public String saveFile(String extension,
                           MultipartFile multipartFile) {
        checkExtension(extension);
        String fileName = String.valueOf(new Date().getTime()).concat(".").concat(extension);
        Path uploadPath = Paths.get(imageRootDirectory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    @SneakyThrows
    public byte[] getImage(String fileName) {
        return Files.readAllBytes(Paths.get(imageRootDirectory, fileName));
    }

    private void checkExtension(String extension) {
        for (String acceptableExtension : acceptableExtensions) {
            if (extension.equals(acceptableExtension)) {
                return;
            }
        }
        throw new FileExtensionNotAllowedException(extension);
    }

    @SneakyThrows
    public byte[] createAndGetPdf(DocumentData data) {
        regular = getTTInterphasesFont(false);
        bold = getTTInterphasesFont(true);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(bos);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        PageSize customPageSize = new PageSize(840F, 1188F);
        Document document = new Document(pdfDocument, customPageSize);
        document.setFont(regular);

        DocumentType documentType = DocumentType.intToEnum(data.getDocumentType());
        switch (documentType) {
            case SHTAT_VAHIDININ_TESISI: {
                createPosition(document, data);
                break;
            }
            case XITAM: {
                createEndJob(document, data);
                break;
            }
            default:
                throw new EnumNotFoundException(DocumentType.class, documentType);
        }
        document.close();
        return bos.toByteArray();
    }


    private void createPosition(Document document, DocumentData documentData) {
        log.info("createPosition PDF creator started with {}", documentData);
        Employee employee = employeeRepository.findById(documentData.getEmployeeId()).orElseThrow(() ->
                new EntityNotFoundException(Employee.class, documentData.getEmployeeId()));
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

        Position position = employee.getPosition();
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
                documentData.getSalary());
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
        Operation saved = save(employee, documentData);
        log.info("********** createPosition PDF creator completed with operationId : {} **********", saved.getId());
    }

    @Transactional
    protected Operation save(Employee employee, DocumentData documentData) {
        Operation operation = Operation.builder()
                .employee(employee)
                .dismissalDate(LocalDate.parse(documentData.getDismissalDate(),
                        DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .dismissalReason(documentData.getDismissalReason())
                .documentType(DocumentType.SHTAT_VAHIDININ_TESISI)
                .build();
        return operationRepository.save(operation);
    }

    private void createEndJob(Document document, DocumentData documentData) {
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
                translatedToAze.getDepartmentName());
        Text text2 = new Text("2. İşlədiyi struktur bölmənin adı:  " + translatedToAze.getVacancyName());
        Text text3 = new Text("3. İşlədiyi alt struktur bölmənin adı: " + translatedToAze.getSubDepartmentName());
        Text text4 = new Text("4. İşçinin vəzifəsi:  " + translatedToAze.getWorkMode());
        Text text5 = new Text("5. İşdən azad olma tarixi:  " + documentData.getDismissalDate());
        Text text6 = new Text("6. İşdən azad olma səbəbi:  " + documentData.getDismissalReason());

        Text text7 = new Text("7.İstifadə edilməmiş məzuniyyət \n" +
                "günlərinə görə kompensasiya:  " + translatedToAze.getWorkPlace());

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
    }

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
