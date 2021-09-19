package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.constant.DocumentType;
import az.hrportal.hrportalapi.domain.operation.Operation;
import az.hrportal.hrportalapi.dto.DocumentData;
import az.hrportal.hrportalapi.dto.KeyValueLabel;
import az.hrportal.hrportalapi.error.exception.DocumentException;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.helper.FileUtil;
import az.hrportal.hrportalapi.mapper.operation.OperationMapper;
import az.hrportal.hrportalapi.repository.OperationRepository;
import az.hrportal.hrportalapi.repository.employee.EmployeeRepository;
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
    private final EmployeeRepository employeeRepository;
    private final OperationMapper operationMapper;

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

    @SneakyThrows
    public Integer create(DocumentData documentData) {
        log.info("create (Document) service started with {}", documentData);
        Operation operation = operationMapper.toOperation(documentData);
        if (documentData.getEmployeeId() != null)
            operation.setEmployee(employeeRepository.findById(documentData.getEmployeeId()).orElseThrow(() ->
                    new EntityNotFoundException(Operation.class, documentData.getEmployeeId())));
        Operation saved = operationRepository.save(operation);
        log.info("********** create (Document) service completed with id : {} **********", saved.getId());
        return saved.getId();
    }

    public Set<KeyValueLabel<String, Integer, String>> getDocumentTypes() {
        log.info("getDocumentTypes service started");
        Set<KeyValueLabel<String, Integer, String>> documentTypes = new HashSet<>();
        for (DocumentType documentType : DocumentType.values()) {
            documentTypes.add(new KeyValueLabel<>(documentType.toString(), documentType.getValue(),
                    documentType.getLabel()));
        }
        log.info("********** getDocumentTypes service completed **********");
        return documentTypes;
    }
}
