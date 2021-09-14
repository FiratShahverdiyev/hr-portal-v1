package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.constant.DocumentType;
import az.hrportal.hrportalapi.dto.DocumentData;
import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.error.exception.DocumentException;
import az.hrportal.hrportalapi.helper.FileUtil;
import az.hrportal.hrportalapi.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentService {
    private final FileUtil fileUtil;
    private final OperationRepository operationRepository;

    @SneakyThrows
    public byte[] export2Pdf(DocumentData documentData) {
        log.info("export2Pdf service started with {}", documentData);
        byte[] response;
        try {
            response = fileUtil.createAndGetPdf(documentData);
        } catch (Exception e) {
            throw new DocumentException(e);
        }
        log.info("********** export2Pdf service completed with {} **********", documentData);
        return response;
    }

    public Set<KeyValue<String, Integer>> getDocumentTypes() {
        log.info("getDocumentTypes service started");
        Set<KeyValue<String, Integer>> documentTypes = new HashSet<>();
        for (DocumentType documentType : DocumentType.values()) {
            documentTypes.add(new KeyValue<>(documentType.toString(), documentType.getValue()));
        }
        log.info("********** getDocumentTypes service completed **********");
        return documentTypes;
    }
}
