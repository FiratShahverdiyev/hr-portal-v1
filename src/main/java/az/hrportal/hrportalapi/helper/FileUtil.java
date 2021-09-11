package az.hrportal.hrportalapi.helper;

import az.hrportal.hrportalapi.constant.DocumentType;
import az.hrportal.hrportalapi.dto.DocumentData;
import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;
import az.hrportal.hrportalapi.error.exception.FileExtensionNotAllowedException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${amazon.s3.bucket.folder.employee-images}")
    private String employeeImagePath;
    @Value("${amazon.s3.bucket.hr-portal}")
    private String bucket;
    @Value("${file.upload.acceptable-extension}")
    private String[] acceptableExtensions;
    @Value("${file.upload.image-root-directory}")
    private String imageRootDirectory;
    private PdfFont regular;
    private PdfFont bold;

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
            return IOUtils.toByteArray(objectContent);
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
    public byte[] createAndGetPdf(DocumentData data) {
        log.info("createAndGetPdf util started with {}", data);
        regular = pdfCreator.getTTInterphasesFont(false);
        bold = pdfCreator.getTTInterphasesFont(true);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(bos);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        PageSize customPageSize = new PageSize(840F, 1188F);
        Document document = new Document(pdfDocument, customPageSize);
        document.setFont(regular);

        DocumentType documentType = DocumentType.intToEnum(data.getDocumentType());
        switch (documentType) {
            case SHTAT_VAHIDININ_TESISI: {
                pdfCreator.pdfCreatePosition(document, data, bold);
                break;
            }
            case SHTAT_VAHIDININ_LEGVI: {
                pdfCreator.pdfDeletePosition(document, data, bold);
                break;
            }
            case SHTAT_EMEK_HAQQININ_DEYISTIRILMESI: {
                pdfCreator.pdfChangeSalary(document, data, bold);
                break;
            }
            case STRUKTURUN_TESIS_EDILMESI: {
                break;
            }
            case XITAM: {
                pdfCreator.pdfEndJob(document, data, bold);
                break;
            }
            default:
                throw new EnumNotFoundException(DocumentType.class, documentType);
        }
        document.close();
        log.info("********** createAndGetPdf util completed with {} **********", data);
        return bos.toByteArray();
    }
}
