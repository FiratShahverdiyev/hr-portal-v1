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

    public void save(Integer id, MultipartFile file) {
        log.info("Image save service started with id : {}, fileName : {}", id, file.getOriginalFilename());
        String fileName = FileUtil.saveFile(fileRootDirectory, file.getOriginalFilename(),
                file.getContentType().split("/")[1], file);
        employeeService.setPhotoName(id, fileName);
        log.info("Image save service completed with id : {}, fileName : {}", id, fileName);
    }

    public byte[] get(String fileName) {
        log.info("Image get service started with fileName : {}", fileName);
        return FileUtil.getImage(fileRootDirectory, fileName);
    }
}
