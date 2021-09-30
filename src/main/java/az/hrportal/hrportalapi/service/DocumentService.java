package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.constant.DocumentType;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.operation.Operation;
import az.hrportal.hrportalapi.domain.position.Position;
import az.hrportal.hrportalapi.dto.DocumentData;
import az.hrportal.hrportalapi.dto.KeyValueLabel;
import az.hrportal.hrportalapi.dto.document.EmployeeDocumentInformation;
import az.hrportal.hrportalapi.dto.document.PositionDocumentInformation;
import az.hrportal.hrportalapi.error.exception.DocumentException;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;
import az.hrportal.hrportalapi.helper.FileUtil;
import az.hrportal.hrportalapi.mapper.document.DocumentInformationResponseMapper;
import az.hrportal.hrportalapi.mapper.operation.OperationMapper;
import az.hrportal.hrportalapi.repository.OperationRepository;
import az.hrportal.hrportalapi.repository.employee.EmployeeRepository;
import az.hrportal.hrportalapi.repository.position.PositionRepository;
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
    private final PositionRepository positionRepository;
    private final OperationMapper operationMapper;
    private final DocumentInformationResponseMapper documentInformationResponseMapper;

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
                    new EntityNotFoundException(Employee.class, documentData.getEmployeeId())));
        if (documentData.getPositionId() != null)
            operation.setPosition(positionRepository.findById(documentData.getPositionId()).orElseThrow(() ->
                    new EntityNotFoundException(Position.class, documentData.getPositionId())));
        Operation saved = operationRepository.save(operation);
        log.info("********** create (Document) service completed with id : {} **********", saved.getId());
        return saved.getId();
    }

    public EmployeeDocumentInformation getEmployeeDocumentInfoById(Integer employeeId) {
        log.info("getEmployeeDocumentInformation service started with positionId : {}", employeeId);
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
                new EntityNotFoundException(Employee.class, employeeId));
        log.info("********** getEmployeeDocumentInformation service completed with positionId : {} **********",
                employeeId);
        return documentInformationResponseMapper.toEmployeeDocumentInformation(employee);
    }

    public PositionDocumentInformation getPositionDocumentInfoById(Integer positionId) {
        log.info("getPositionDocumentInformation service started with positionId : {}", positionId);
        Position position = positionRepository.findById(positionId).orElseThrow(() ->
                new EntityNotFoundException(Position.class, positionId));
        log.info("********** getPositionDocumentInformation service completed with positionId : {} **********",
                positionId);
        return documentInformationResponseMapper.toPositionDocumentInformation(position);
    }

    public Set<Integer> getAllPositionsId() {
        log.info("getAllPositionsId service started");
        Set<Integer> response = new HashSet<>();
        for (Position position : positionRepository.findAll()) {
            response.add(position.getId());
        }
        log.info("********** getAllPositionsId service completed **********");
        return response;
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

    protected void checkAndDo(DocumentType documentType) {
       
    }
}
