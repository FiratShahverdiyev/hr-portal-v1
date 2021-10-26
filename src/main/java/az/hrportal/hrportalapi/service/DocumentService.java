package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.constant.DocumentType;
import az.hrportal.hrportalapi.constant.EmployeeActivity;
import az.hrportal.hrportalapi.constant.Status;
import az.hrportal.hrportalapi.domain.employee.Employee;
import az.hrportal.hrportalapi.domain.operation.Operation;
import az.hrportal.hrportalapi.domain.position.Position;
import az.hrportal.hrportalapi.dto.KeyValueLabel;
import az.hrportal.hrportalapi.dto.PaginationResponseDto;
import az.hrportal.hrportalapi.dto.document.DocumentData;
import az.hrportal.hrportalapi.dto.document.DocumentResponseDto;
import az.hrportal.hrportalapi.dto.document.EmployeeDocumentInformation;
import az.hrportal.hrportalapi.dto.document.GeneralDocumentInformation;
import az.hrportal.hrportalapi.dto.document.PositionDocumentInformation;
import az.hrportal.hrportalapi.error.exception.EmployeeNotActiveException;
import az.hrportal.hrportalapi.error.exception.EntityNotFoundException;
import az.hrportal.hrportalapi.error.exception.ValidationException;
import az.hrportal.hrportalapi.helper.CommonHelper;
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
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
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

    public byte[] export2Pdf(Integer operationId, HttpServletResponse httpServletResponse) {
        log.info("export2Pdf service started with operationId : {}", operationId);
        Operation operation = operationRepository.findById(operationId)
                .orElseThrow(() -> new EntityNotFoundException(Operation.class, operationId));
        byte[] response = fileUtil.createAndGetPdf(operation);
        String fileName = operation.getDocumentType().getValueAz();
        httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + fileName);
        httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, "application/pdf");
        log.info("********** export2Pdf service completed with operationId : {} **********", operationId);
        return response;
    }

    @SneakyThrows
    public Integer create(DocumentData documentData) {
        log.info("create (Document) service started with {}", documentData);
        validate(documentData);
        Operation operation = operationMapper.toOperation(documentData);
        if (documentData.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(documentData.getEmployeeId()).orElseThrow(() ->
                    new EntityNotFoundException(Employee.class, documentData.getEmployeeId()));
            if (!DocumentType.intToEnum(documentData.getDocumentType()).equals(DocumentType.ISHE_QEBUL) &&
                    !employee.getEmployeeActivity().equals(EmployeeActivity.IN)) {
                throw new EmployeeNotActiveException(employee.getFullName() + " id : " + employee.getId());
            }
            operation.setEmployee(employee);
        }
        if (documentData.getPositionId() != null) {
            Position position = positionRepository.findById(documentData.getPositionId()).orElseThrow(() ->
                    new EntityNotFoundException(Position.class, documentData.getPositionId()));
            if (CommonHelper.checkStatus(position))
                operation.setPosition(position);
            else
                throw new RuntimeException("Position isn't approved");
        }
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
            if (CommonHelper.checkStatus(position))
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
                    throw new ValidationException("employeeId,newSalary,changeDate,newAdditionalSalary");
                break;
            }
            case ISH_REJIMININ_DEYISTIRILMESI: {
                if (documentData.getEmployeeId() == null || documentData.getNewWorkMode() == null ||
                        documentData.getNewSalary() == null)
                    throw new ValidationException("employeeId,newWorkMode,newSalary");
                break;
            }
            case ISH_YERI_DEYISIKLIYI: {
                if (documentData.getEmployeeId() == null || documentData.getPositionId() == null ||
                        documentData.getNewOwnAdditionalSalary() == null || documentData.getChangeDate() == null)
                    throw new ValidationException("employeeId,positionId,newOwnAdditionalSalary,changeDate");
                break;
            }
            case MUVEQQETI_KECIRILME: {
                if (documentData.getEmployeeId() == null || documentData.getPositionId() == null ||
                        documentData.getNewOwnAdditionalSalary() == null || documentData.getChangeDate() == null ||
                        documentData.getNewTerm() == null)
                    throw new ValidationException("employeeId,positionId," +
                            "newOwnAdditionalSalary,changeDate,changePeriod");
                break;
            }
            case MUVEQQETI_HEVALE: {
                if (documentData.getEmployeeId() == null || documentData.getPositionId() == null ||
                        documentData.getChangeDate() == null)
                    throw new ValidationException("employeeId, positionId" + "changeDate");
                break;
            }
            case SECKIDE_ISTIRAK:
            case TEHSIL_YARADICILIQ_MEZUNIYYETI:
            case ISCIYE_ODENISSIZ_MEZUNIYYET:
            case ISCIYE_SOSIAL_MEZUNIYYET:
            case QISMEN_ODENISHLI_SOSIAL_MEZUNIYYET:
            case ODENISHLI_ISTIRAHET_GUNU: {
                if (documentData.getEmployeeId() == null || documentData.getDayInEvent() == null ||
                        documentData.getEventFrom() == null || documentData.getEventTo() == null ||
                        documentData.getJoinDate() == null)
                    throw new ValidationException("employeeId," +
                            "dayInEvent, eventFrom, eventTo, joinDate");
                break;
            }
            case MEZUNIYYET_VERILMESI: {
                if (documentData.getEmployeeId() == null || documentData.getDayInEvent() == null ||
                        documentData.getEventFrom() == null || documentData.getEventTo() == null ||
                        documentData.getJoinDate() == null)
                    throw new ValidationException("employeeId," +
                            "dayInEvent, eventFrom, eventTo, joinDate");
                break;
            }
            case MEZUNIYYETIN_UZADILMASI: {
                if (documentData.getEmployeeId() == null ||
                        documentData.getDayInEvent() == null || documentData.getEventFrom() == null ||
                        documentData.getEventTo() == null || documentData.getJoinDate() == null ||
                        documentData.getEventFrom2() == null || documentData.getEventTo2() == null)
                    throw new ValidationException("employeeId," +
                            "dayInEvent, eventFrom, eventTo, joinDate, eventFrom2, eventTo2,");
                break;
            }
            case MEZUNIYYETDEN_GERI_QAYITMA: {
                if (documentData.getEmployeeId() == null ||
                        documentData.getEventFrom() == null || documentData.getEventTo() == null ||
                        documentData.getCallBackDate() == null || documentData.getCallBackReason() == null)
                    throw new ValidationException("employeeId," +
                            "eventFrom, eventTo, callBackDate, callBackReason");
                break;
            }
            case ODENISSIZ_MEZUNIYYETDEN_CAGIRILMA: {
                if (documentData.getEmployeeId() == null ||
                        documentData.getEventFrom() == null || documentData.getEventTo() == null ||
                        documentData.getJoinDate() == null || documentData.getCallBackDate() == null)
                    throw new ValidationException("employeeId," +
                            "eventFrom, eventTo, joinDate, callBackDate");
                break;
            }
            case ISHE_CELB_EDILME:
            case KOMPENSASIYA_ODENILMESI: {
                if (documentData.getEmployeeId() == null || documentData.getReason() == null)
                    throw new ValidationException("employeeId, reason");
                break;
            }
            case QEYRI_IS_GUNU:
            case MEZUNIYYET_QRAFIKININ_TESDIQI: {
                if (documentData.getYear() == null)
                    throw new ValidationException("year");
                break;
            }
            case TELIME_GONDERILME: {
                if (documentData.getEmployeeId() == null ||
                        documentData.getEventFrom() == null || documentData.getEventTo() == null ||
                        documentData.getEventName() == null)
                    throw new ValidationException("employeeId," +
                            "eventFrom, eventTo, eventName");
                break;
            }
            case TELIM_PLANININ_TESDIQI: {
                break;
            }
            case EZAMIYYETE_GONDERILME:
            case EZAMIYYETIN_UZADILMASI: {
                if (documentData.getEmployeeId() == null ||
                        documentData.getBusinessTripLocation() == null ||
                        documentData.getEventFromBusinessTripDate() == null ||
                        documentData.getEventToBusinessTripDate() == null ||
                        documentData.getBusinessTripTerm() == null || documentData.getNonWorkDay() == null ||
                        documentData.getGivenNonWorkDay() == null || documentData.getJoinDate() == null)
                    throw new ValidationException("employeeId,businessTripLocation ," +
                            "eventFromBusinessTripDate, eventToBusinessTripDate, nonWorkDay, givenNonWorkDay, " +
                            "businessTripTerm, joinDate");
                break;
            }
            case EZAMIYYETDEN_GERI_CAGIRILMA: {
                if (documentData.getEmployeeId() == null ||
                        documentData.getBusinessTripLocation() == null ||
                        documentData.getEventFromBusinessTripDate() == null ||
                        documentData.getEventToBusinessTripDate() == null ||
                        documentData.getBusinessTripTerm() == null ||
                        documentData.getCallBackDate() == null)
                    throw new ValidationException("employeeId,businessTripLocation ," +
                            "eventFromBusinessTripDate, eventToBusinessTripDate," +
                            " callBackDate, businessTripTerm");
                break;
            }
            case ISCININ_ISDEN_KENARLASDIRILMASI:
            case HERBI_CAGIRISH: {
                if (documentData.getEmployeeId() == null ||
                        documentData.getDayInEvent() == null)
                    throw new ValidationException("employeeId, dayInEvent");
                break;
            }
            case TEDBIRDE_ISTIRAK: {
                if (documentData.getEmployeeId() == null ||
                        documentData.getEventFrom() == null || documentData.getEventTo() == null ||
                        documentData.getDayInEvent() == null)
                    throw new ValidationException("employeeId," +
                            "eventFrom, eventTo, dayInEvent");
                break;
            }
            case ELAVE_ISTIRAHET_GUNU_VERILMESI: {
                if (documentData.getEmployeeId() == null ||
                        documentData.getEventFrom() == null || documentData.getEventTo() == null ||
                        documentData.getDayInEvent() == null || documentData.getJoinDate() == null)
                    throw new ValidationException("employeeId," +
                            "eventFrom, eventTo, dayInEvent, joinDate");
                break;
            }
            case MUAVINETIN_TEYIN_OLUNMASI: {
                if (documentData.getEmployeeId() == null ||
                        documentData.getAmount() == null || documentData.getEventFrom() == null ||
                        documentData.getEventTo() == null)
                    throw new ValidationException("employeeId,amount, eventFrom, eventTo");
                break;
            }
            //Pdf in icerisinde yeni fieldler teyin olunmalidi
            case ISH_SAATININ_QISALDILMASI: {
                if (documentData.getEmployeeId() == null)
                    throw new ValidationException("employeeId");
                break;
            }
            case XEBERDARLIQ: {
                if (documentData.getPresentationOwnerDepartment() == null || documentData.getEmployeeIds() == null ||
                        documentData.getPresentationOwnerPosition() == null)
                    throw new ValidationException("presentationOwnerDepartment, presentationOwnerPosition");
                break;
            }
            case EMEK_HAQQINDAN_TUTULMA: {
                if (documentData.getEmployeeId() == null ||
                        documentData.getCatchAmount() == null || documentData.getCatchMonths() == null)
                    throw new ValidationException("employeeId, catchAmount, catchMonths");
                break;
            }
            case INTIZAM_TENBEHI: {
                if (documentData.getEmployeeId() == null ||
                        documentData.getDisciplineType().getValue() == null || documentData.getDayInEvent() == null ||
                        documentData.getPresentationOwnerName() == null ||
                        documentData.getPresentationOwnerDepartment() == null ||
                        documentData.getPresentationOwnerPosition() == null)
                    throw new ValidationException("employeeId, disciplineType, dayInEvent, " +
                            "presentationOwnerName, presentationOwnerDepartment, presentationOwnerPosition");
                break;
            }
            case MADDI_YARDIM: {
                if (documentData.getEmployeeId() == null || documentData.getFinancialHelp() == null)
                    throw new ValidationException("employeeId,financialHelp");
                break;
            }
            case MUKAFATLANDIRMA: {
                if (documentData.getEmployeeId() == null || documentData.getAchievementAmount() == null)
                    throw new ValidationException("employeeId,achievementAmount");
                break;
            }
            case MUVEQQETI_EVEZETME: {
                if (documentData.getEmployeeId() == null || documentData.getPositionId() == null ||
                        documentData.getNewTerm() == null)
                    throw new ValidationException("employeeId,positionId,changePeriod");
                break;
            }
            case SHTAT_EMEK_HAQQINA_ELAVE: {
                if (documentData.getEmployeeId() == null || documentData.getNewAdditionalSalary() == null)
                    throw new ValidationException("employeeId,newOwnAdditionalSalary");
                break;
            }
            default:
                break;
        }
    }
}
