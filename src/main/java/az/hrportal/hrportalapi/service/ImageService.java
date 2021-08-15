package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.helper.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {
    private final EmployeeService employeeService;

    @Value("${file.upload.root-directory}")
    private String fileRootDirectory;

    public String save(Integer id, MultipartFile file) {
        return FileUtil.saveFile(fileRootDirectory, file.getOriginalFilename(),
                file.getContentType().split("/")[1], file);
    }

    public byte[] get(String fileName) {
        return FileUtil.getImage(fileRootDirectory, fileName);
    }
}
