package az.hrportal.hrportalapi.helper;

import az.hrportal.hrportalapi.error.exception.FileExtensionNotAllowedException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Component
@Slf4j
public class FileUtil {
    @Value("${file.upload.acceptable-extension}")
    private String[] acceptableExtensions;
    @Value("${file.upload.document-root-directory}")
    private String documentRootDirectory;
    @Value("${file.upload.image-root-directory}")
    private String imageRootDirectory;
    private final String pdfExtension = ".pdf";

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
    public byte[] generatePDFFromHTML(String fileName) {
        log.info("generatePDFFromHTML util started with fileName: {}", fileName);
        PdfReader pdfReader = new PdfReader(documentRootDirectory + fileName + pdfExtension);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfStamper pdfStamper = new PdfStamper(pdfReader, bos);
        BaseFont baseFont = BaseFont.createFont(
                BaseFont.TIMES_ROMAN,
                BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        int pages = pdfReader.getNumberOfPages();
        for (int i = 1; i <= pages; i++) {
            PdfContentByte pageContentByte =
                    pdfStamper.getOverContent(i);
            pageContentByte.beginText();
            pageContentByte.setFontAndSize(baseFont, 14);
            pageContentByte.setTextMatrix(50, 740);
            pageContentByte.showTextAligned(0, "A", 355, 447, 0);
            pageContentByte.endText();
        }
        pdfStamper.close();
        log.info("********** generatePDFFromHTML util completed with fileName: {} **********", fileName);
        return bos.toByteArray();
    }
}
