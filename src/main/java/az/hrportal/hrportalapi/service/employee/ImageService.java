package az.hrportal.hrportalapi.service.employee;

import az.hrportal.hrportalapi.helper.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {
    private final EmployeeService employeeService;
    private final FileUtil fileUtil;

    public void save(Integer id, MultipartFile file) {
        log.info("Image save service started with id : {}, fileName : {}", id, file.getOriginalFilename());
        String fileName = fileUtil.saveImage(file);
        employeeService.setPhotoName(id, fileName);
        log.info("Image save service completed with id : {}, fileName : {}", id, fileName);
    }

    public byte[] get(String fileName) {
        log.info("Image get service started with fileName : {}", fileName);
        return fileUtil.getImage(fileName);
    }

    public void uploadS3(Integer id, MultipartFile file) {
        log.info("Image uploadS3 service started with id : {}, fileName : {}", id, file.getOriginalFilename());
        String fileName = fileUtil.saveImageS3(file);
        employeeService.setPhotoName(id, fileName);
        log.info("Image uploadS3 service completed with id : {}, fileName : {}", id, fileName);
    }

    public byte[] getS3(String fileName) {
        log.info("Image getS3 service started with fileName : {}", fileName);
        return fileUtil.getImageS3(fileName);
    }
}
