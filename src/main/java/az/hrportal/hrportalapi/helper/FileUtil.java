package az.hrportal.hrportalapi.helper;

import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtil {

    @SneakyThrows
    public static String saveFile(String fileUploadDirectory, String fileName, String extension,
                                  MultipartFile multipartFile) {
        Path uploadPath = Paths.get(fileUploadDirectory);

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
    public static byte[] getImage(String fileRootDirectory, String fileName) {
        return Files.readAllBytes(Paths.get(fileRootDirectory, fileName));
    }
}
