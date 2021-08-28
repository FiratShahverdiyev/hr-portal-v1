package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.dto.DocumentData;
import az.hrportal.hrportalapi.helper.FileUtil;
import az.hrportal.hrportalapi.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentService {
    private final FileUtil fileUtil;
    private final OperationRepository operationRepository;

    @SneakyThrows
    public byte[] export2Pdf(DocumentData documentData) {
        log.info("export2Pdf service started with {}", documentData);
        byte[] response = fileUtil.createAndGetPdf(documentData);
        log.info("********** export2Pdf service completed with {} **********", documentData);
        return response;
    }
}
