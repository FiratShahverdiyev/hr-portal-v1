package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.constant.DocumentType;
import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;
import az.hrportal.hrportalapi.helper.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentService {
    private final FileUtil fileUtil;

    @SneakyThrows
    public void export2Pdf(String fileType, HttpServletResponse httpServletResponse, HashMap<String, String> data) {
        log.info("export2Pdf service started with fileType : {}", fileType);
        String fileName = fileType2Name(fileType);
        httpServletResponse.getOutputStream().write(fileUtil.html2Pdf(fileType2Name(fileType), data));
        httpServletResponse.setHeader("Content-Disposition",
                "attachment; filename=\"" + fileName + ".pdf\"");
        log.info("********** export2Pdf service completed with fileType : {} **********", fileType);
    }

    private String fileType2Name(String fileType) {
        DocumentType documentType = DocumentType.valueOf(fileType);
        switch (documentType) {
            case A:
                return "test";
            case B:
                return "2";
            case C:
                return "3";
            case D:
                return "4";
            case E:
                return "5";
            default:
                throw new EnumNotFoundException(DocumentType.class, documentType);
        }

    }
}
