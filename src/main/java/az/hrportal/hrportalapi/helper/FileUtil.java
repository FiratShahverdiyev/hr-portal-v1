package az.hrportal.hrportalapi.helper;

import az.hrportal.hrportalapi.error.exception.DocumentException;
import az.hrportal.hrportalapi.error.exception.FileExtensionNotAllowedException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;

import static az.hrportal.hrportalapi.error.ErrorHandlerUtil.getStackTrace;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileUtil {
    @Value("${file.upload.acceptable-extension}")
    private String[] acceptableExtensions;
    @Value("${file.upload.document-root-directory}")
    private String documentRootDirectory;
    @Value("${file.upload.image-root-directory}")
    private String imageRootDirectory;

    private final TemplateEngine templateEngine;

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

    public byte[] html2Pdf(String templateName, HashMap<String, String> data) {
        log.info("html2Pdf util started with templateName: {}", templateName);
        Context ctx = new Context();
        for (String key : data.keySet()) {
            ctx.setVariable(key, data.get(key));
        }
        try {
            String processedHtml = templateEngine.process(templateName, ctx);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(processedHtml);
            renderer.layout();
            renderer.createPDF(bos, false);
            renderer.finishPDF();
            log.info("********** html2Pdf util completed with templateName: {} **********", templateName);
            return bos.toByteArray();
        } catch (Exception e) {
            log.error("---------- Error thrown while HTML2PDF. Exception ---------- \n StackTrace : {}", getStackTrace(e));
            throw new DocumentException(e.getMessage());
        }
    }
}
