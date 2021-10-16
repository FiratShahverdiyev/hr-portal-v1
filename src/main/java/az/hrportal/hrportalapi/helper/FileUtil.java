package az.hrportal.hrportalapi.helper;

import az.hrportal.hrportalapi.constant.DocumentType;
import az.hrportal.hrportalapi.domain.operation.Operation;
import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;
import az.hrportal.hrportalapi.error.exception.FileExtensionNotAllowedException;
import az.hrportal.hrportalapi.repository.OperationRepository;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.interceptor.CacheAspectSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileUtil {
    private final PdfCreator pdfCreator;
    private final AmazonS3 amazonS3;
    private final OperationRepository operationRepository;

    @Value("${amazon.s3.bucket.folder.employee-images}")
    private String employeeImagePath;
    @Value("${amazon.s3.bucket.hr-portal}")
    private String bucket;
    @Value("${file.upload.acceptable-extension}")
    private String[] acceptableExtensions;
    @Value("${file.upload.image-root-directory}")
    private String imageRootDirectory;

    @SneakyThrows
    public String saveImage(MultipartFile file) {
        log.info("saveImage util started");
        String extension = file.getContentType().split("/")[1];
        checkExtension(extension);
        String fileName = generateFileName(extension);
        Path uploadPath = Paths.get(imageRootDirectory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            log.info("********** saveImage util completed with fileName : {} **********", fileName);
            return fileName;
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    @SneakyThrows
    public String saveImageS3(MultipartFile file) {
        try {
            log.info("saveImageS3 util started");
            String extension = file.getContentType().split("/")[1];
            checkExtension(extension);
            String fileName = generateFileName(extension);
            String path = bucket + "/" + employeeImagePath;
            amazonS3.putObject(path, fileName, file.getInputStream(), null);
            log.info("********** saveImageS3 util completed with fileName : {} **********", fileName);
            return fileName;
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }

    public byte[] getImageS3(String fileName) {
        try {
            String path = bucket + "/" + employeeImagePath;
            S3Object object = amazonS3.getObject(path, fileName);
            S3ObjectInputStream objectContent = object.getObjectContent();
            return objectContent.readAllBytes();
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download the file", e);
        }
    }

    private String generateFileName(String extension) {
        return String.valueOf(UUID.randomUUID().toString()).concat(".").concat(extension);
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
    public byte[] createAndGetPdf(Operation operation) {
        log.info("createAndGetPdf util started");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(bos);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        PageSize customPageSize = new PageSize(840F, 1188F);
        Document document = new Document(pdfDocument, customPageSize);
        document.setLeftMargin(100f);
        document.setTopMargin(320f);
        pdfCreator.createFont();
        checkAndCreatePdf(document, operation);
        document.close();
        log.info("********** createAndGetPdf util completed with **********");
        return bos.toByteArray();
    }

    private void checkAndCreatePdf(Document document, Operation operation) {
        DocumentType documentType = operation.getDocumentType();
        switch (documentType) {
            case SHTAT_VAHIDININ_TESISI: {
                pdfCreator.pdfCreatePosition(document, operation);
                break;
            }
            case SHTAT_VAHIDININ_LEGVI: {
                pdfCreator.pdfDeletePosition(document, operation);
                break;
            }
            case ISHE_QEBUL: {
                pdfCreator.pdfJoinToJob(document, operation);
                break;
            }
            case XITAM: {
                pdfCreator.pdfEndJob(document, operation);
                break;
            }
            case VEZIFE_DEYISIKLIYI: {
                pdfCreator.pdfChangeEmployeePosition(document, operation);
                break;
            }
            case EMEK_HAQQI_DEYISIKLIYI: {
                pdfCreator.pdfChangeEmployeeSalary(document, operation);
                break;
            }
            case ELAVE_EMEK_HAQQI: {
                pdfCreator.pdfAdditionalSalary(document, operation);
                break;
            }
            case ISH_REJIMININ_DEYISTIRILMESI: {
                pdfCreator.pdfChangeWorkMode(document, operation);
                break;
            }
            case MUVEQQETI_EVEZETME: {
                pdfCreator.pdfTemporaryChange(document, operation);
                break;
            }
            case MADDI_YARDIM: {
                pdfCreator.pdfFinancialHelp(document, operation);
                break;
            }
            case ISH_YERI_DEYISIKLIYI: {
                pdfCreator.pdfChangeWorkPlace(document, operation);
                break;
            }
            case MUKAFATLANDIRMA: {
                pdfCreator.pdfAchievement(document, operation);
                break;
            }
            case MUVEQQETI_KECIRILME: {
                pdfCreator.pdfTemporaryPass(document, operation);
                break;
            }
            case SHTAT_EMEK_HAQQINA_ELAVE: {
                pdfCreator.pdfPositionAdditionalSalary(document, operation);
                break;
            }
            case TELIME_GONDERILME: {
                pdfCreator.pdfEmployeeToTraining(document, operation);
                break;
            }
            case SECKIDE_ISTIRAK: {
                pdfCreator.pdfEmployeeToSelection(document, operation);
                break;
            }
            case TELIM_PLANININ_TESDIQI: {
                pdfCreator.pdfApproveTrainingPlan(document, operation);
                break;
            }
            case TEDBIRDE_ISTIRAK: {
                pdfCreator.pdfAttendanceInTraining(document, operation);
                break;
            }
            case INTIZAM_TENBEHI: {
                pdfCreator.pdfDisciplineAction(document, operation);
                break;
            }
            case XEBERDARLIQ: {
                pdfCreator.pdfWarning(document, operation);
                break;
            }
            case ODENISSIZ_MEZUNIYYETDEN_CAGIRILMA: {
                pdfCreator.pdfCallBackFromVacation(document, operation);
                break;
            }
            case MEZUNIYYET_QRAFIKININ_TESDIQI: {
                pdfCreator.pdfApproveVacationChart(document, operation);
                break;
            }
            case EMEK_HAQQINDAN_TUTULMA: {
                pdfCreator.pdfCatchFromSalary(document, operation);
                break;
            }
            case QEYRI_IS_GUNU: {
                pdfCreator.pdfNonActiveDay(document, operation);
                break;
            }
            case MUVEQQETI_HEVALE: {
                pdfCreator.pdfTemporaryAssignment(document, operation);
                break;
            }
            case EZAMIYYETE_GONDERILME: {
                pdfCreator.pdfGoOnBusinessTrip(document, operation);
                break;
            }
            case QISMEN_ODENISHLI_SOSIAL_MEZUNIYYET: {
                pdfCreator.pdfPaidSocialVacation(document, operation);
                break;
            }
            case ODENISHLI_ISTIRAHET_GUNU: {
                pdfCreator.pdfPaidDayOff(document, operation);
                break;
            }
            case ISCIYE_SOSIAL_MEZUNIYYET: {
                pdfCreator.pdfSocialVacation(document, operation);
                break;
            }
            case MEZUNIYYETIN_UZADILMASI: {
                pdfCreator.pdfIncreaseVacation(document, operation);
                break;
            }
            case EZAMIYYETIN_UZADILMASI: {
                pdfCreator.pdfIncreaseBusinessTrip(document, operation);
                break;
            }
            case MEZUNIYYET_VERILMESI: {
                pdfCreator.pdfGiveVacation(document, operation);
                break;
            }
            case EZAMIYYETDEN_GERI_CAGIRILMA: {
                pdfCreator.pdfCallBackFromBusinessTrip(document, operation);
                break;
            }
            case ISH_SAATININ_QISALDILMASI: {
                pdfCreator.pdfDecreaseWorkHours(document, operation);
                break;
            }
            case TEHSIL_YARADICILIQ_MEZUNIYYETI: {
                pdfCreator.pdfEducationVacation(document, operation);
                break;
            }
            case HERBI_CAGIRISH: {
                pdfCreator.pdfMilitaryCalls(document, operation);
                break;
            }
            case ELAVE_ISTIRAHET_GUNU_VERILMESI: {
                pdfCreator.pdfAdditionalDayOff(document, operation);
                break;
            }
            case MUAVINETIN_TEYIN_OLUNMASI: {
                pdfCreator.pdfSetAllowance(document, operation);
                break;
            }
            case MEZUNIYYETDEN_GERI_QAYITMA: {
                pdfCreator.pdfCallBackFromWorkingVacation(document, operation);
                break;
            }
            case KOMPENSASIYA_ODENILMESI: {
                pdfCreator.pdfPaymentOfCompensation(document, operation);
                break;
            }
            case ISHE_CELB_EDILME: {
                pdfCreator.pdfAttractToWork(document, operation);
                break;
            }
            default:
                throw new EnumNotFoundException(DocumentType.class, documentType);
        }
    }
}
