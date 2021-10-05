package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.constant.DocumentType;
import az.hrportal.hrportalapi.constant.Status;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.operation.Operation;
import az.hrportal.hrportalapi.domain.position.Position;
import az.hrportal.hrportalapi.domain.position.Salary;
import az.hrportal.hrportalapi.dto.KeyValueLabel;
import az.hrportal.hrportalapi.dto.PaginationResponseDto;
import az.hrportal.hrportalapi.dto.document.DocumentData;
import az.hrportal.hrportalapi.dto.document.DocumentResponseDto;
import az.hrportal.hrportalapi.dto.document.EmployeeDocumentInformation;
import az.hrportal.hrportalapi.dto.document.GeneralDocumentInformation;
import az.hrportal.hrportalapi.dto.document.PositionDocumentInformation;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.error.exception.ValidationException;
import az.hrportal.hrportalapi.helper.FileUtil;
import az.hrportal.hrportalapi.mapper.document.DocumentInformationResponseMapper;
import az.hrportal.hrportalapi.mapper.document.DocumentResponseMapper;
import az.hrportal.hrportalapi.mapper.operation.OperationMapper;
import az.hrportal.hrportalapi.repository.OperationRepository;
import az.hrportal.hrportalapi.repository.employee.EmployeeRepository;
import az.hrportal.hrportalapi.repository.position.PositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
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
    private final DocumentResponseMapper documentResponseMapper;

    public byte[] export2Pdf(Integer operationId) {
        log.info("export2Pdf service started with operationId : {}", operationId);
        byte[] response = fileUtil.createAndGetPdf(operationId);
        log.info("********** export2Pdf service completed with operationId : {} **********", operationId);
        return response;
    }

    @SneakyThrows
    public Integer create(DocumentData documentData) {
        log.info("create (Document) service started with {}", documentData);
        validate(documentData);
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

    @Transactional
    public Integer changeStatus(Integer id, Integer status) {
        log.info("changeStatus service started with id : {}, status : {}", id, status);
        Operation operation = operationRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Operation.class, id));
        operation.setStatus(Status.intToEnum(status));
        if (status == 1) {
            checkAndDo(operation);
        }
        Operation saved = operationRepository.save(operation);
        log.info("changeStatus service completed with id : {}, status : {}", id, status);
        return saved.getId();
    }

    public GeneralDocumentInformation getDocumentById(Integer id) {
        log.info("getDocumentById service started with id : {}", id);
        Operation operation = operationRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Operation.class, id));
        log.info("********** getDocumentById service completed with id : {} **********", id);
        return documentResponseMapper.toGeneralDocumentInformation(operation);
    }

    public PaginationResponseDto<List<DocumentResponseDto>> getDocuments(int page, int size) {
        log.info("getDocuments service started");
        List<DocumentResponseDto> data = documentResponseMapper
                .toDocumentResponseDtos(operationRepository.findAll(Sort.by("createdAt").descending()));
        PagedListHolder<DocumentResponseDto> pagedListHolder = new PagedListHolder<>(data);
        pagedListHolder.setPage(page);
        pagedListHolder.setPageSize(size);
        List<DocumentResponseDto> response = pagedListHolder.getPageList();
        log.info("********** getDocuments service completed **********");
        return new PaginationResponseDto<>(response, response.size(), data.size());
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
            documentTypes.add(new KeyValueLabel<>(documentType.getValueAz(), documentType.getValue(),
                    documentType.getLabel()));
        }
        log.info("********** getDocumentTypes service completed **********");
        return documentTypes;
    }

    @Transactional
    protected void checkAndDo(Operation operation) {
        switch (operation.getDocumentType()) {
            case SHTAT_VAHIDININ_LEGVI: {
                Position position = operation.getPosition();
                position.setStatus(Status.REJECTED);
                positionRepository.save(position);
                break;
            }
            case SHTAT_VAHIDININ_TESISI: {
                Position position = operation.getPosition();
                position.setStatus(Status.APPROVED);
                positionRepository.save(position);
                break;
            }
            case ISHE_QEBUL: {
                Employee employee = operation.getEmployee();
                Position position = operation.getPosition();
                employee.setPosition(position);
                employee.setActive(true);
                employee.setOwnAdditionalSalary(operation.getOwnAdditionalSalary());
                employeeRepository.save(employee);
                break;
            }
            case XITAM: {
                Employee employee = operation.getEmployee();
                employee.setActive(false);
                break;
            }
            case VEZIFE_DEYISIKLIYI: {
                Employee employee = operation.getEmployee();
                Position position = operation.getPosition();
                employee.setPosition(position);
                employee.setOwnAdditionalSalary(operation.getNewOwnAdditionalSalary());
                employeeRepository.save(employee);
                break;
            }

            case EMEK_HAQQI_DEYISIKLIYI: {
                Employee employee = operation.getEmployee();
                Position position = employee.getPosition();
                Salary salary = new Salary();
                salary.setSalary(operation.getNewSalary());
                position.setSalary(salary);
                position.setAdditionalSalary(operation.getNewAdditionalSalary());
                employee.setOwnAdditionalSalary(operation.getNewOwnAdditionalSalary());
                employeeRepository.save(employee);
                positionRepository.save(position);
                break;
            }
            case ELAVE_EMEK_HAQQI: {
                Employee employee = operation.getEmployee();
                Position position = employee.getPosition();
                Salary salary = new Salary();
                salary.setSalary(operation.getNewSalary());
                position.setSalary(salary);
                position.setAdditionalSalary(operation.getNewAdditionalSalary());
                positionRepository.save(position);
                break;
            }
            case ISH_REJIMININ_DEYISTIRILMESI: {
                Employee employee = operation.getEmployee();
                Position position = employee.getPosition();
                position.setWorkMode(operation.getWorkMode());
                positionRepository.save(position);
                break;
            }
            default: {
                break;
            }
        }
    }

    private void validate(DocumentData documentData) {
        DocumentType documentType = DocumentType.intToEnum(documentData.getDocumentType());
        switch (documentType) {
            case SHTAT_VAHIDININ_TESISI:
            case SHTAT_VAHIDININ_LEGVI: {
                if (documentData.getPositionId() == null)
                    throw new ValidationException("positionId");
                break;
            }
            case ISHE_QEBUL: {
                if (documentData.getEmployeeId() == null || documentData.getPositionId() == null ||
                        documentData.getJoinDate() == null || documentData.getTestPeriod() == null ||
                        documentData.getOwnAdditionalSalary() == null)
                    throw new ValidationException("positionId,employeeId,joinDate,testPeriod,ownAdditionalSalary");
                break;
            }
            case XITAM: {
                if (documentData.getEmployeeId() == null || documentData.getDismissalDate() == null ||
                        documentData.getDismissalReason() == null)
                    throw new ValidationException("employeeId,dismissalDate,dismissalReason");
                break;
            }
            case VEZIFE_DEYISIKLIYI: {
                if (documentData.getEmployeeId() == null || documentData.getPositionId() == null ||
                        documentData.getChangeDate() == null || documentData.getNewOwnAdditionalSalary() == null)
                    throw new ValidationException("positionId,employeeId,changeDate,newOwnAdditionalSalary");
                break;
            }
            case EMEK_HAQQI_DEYISIKLIYI: {
                if (documentData.getEmployeeId() == null || documentData.getNewSalary() == null ||
                        documentData.getNewAdditionalSalary() == null || documentData.getChangeDate() == null ||
                        documentData.getNewOwnAdditionalSalary() == null)
                    throw new ValidationException("newSalary,employeeId,changeDate,newOwnAdditionalSalary, " +
                            "newAdditionalSalary");
                break;
            }
            case ELAVE_EMEK_HAQQI: {
                if (documentData.getEmployeeId() == null || documentData.getNewSalary() == null ||
                        documentData.getChangeDate() == null || documentData.getNewAdditionalSalary() == null)
                    throw new ValidationException("employeeId,newSalary,changeDate,newAdditionalSalary" +
                            "");
                break;
            }
            case ISH_REJIMININ_DEYISTIRILMESI: {
                if (documentData.getEmployeeId() == null || documentData.getNewWorkMode() == null ||
                        documentData.getNewSalary() == null)
                    throw new ValidationException("employeeId,workMode");
                break;
            }
            default:
                break;
        }
    }
}
