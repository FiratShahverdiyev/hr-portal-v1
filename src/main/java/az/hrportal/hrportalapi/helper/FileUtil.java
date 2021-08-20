package az.hrportal.hrportalapi.helper;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUtil {
    @Value("${file.upload.acceptable-extension}")
    private String[] acceptableExtensions;
    @Value("${file.upload.root-directory}")
    private String fileRootDirectory;

    @SneakyThrows
    public String saveFile(String fileName, String extension,
                           MultipartFile multipartFile) {
        checkExtension(extension);
        Path uploadPath = Paths.get(fileRootDirectory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName.concat(".").concat(extension));
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.getName(filePath.getNameCount() - 1).toString();
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    @SneakyThrows
    public byte[] getImage(String fileName) {
        return Files.readAllBytes(Paths.get(fileRootDirectory, fileName));
    }

    private void checkExtension(String extension) {
        for (String acceptableExtension : acceptableExtensions) {
            if (extension.equals(acceptableExtension)) {
                return;
            }
        }
        throw new RuntimeException("Not allowed file extension!!");
    }
}
