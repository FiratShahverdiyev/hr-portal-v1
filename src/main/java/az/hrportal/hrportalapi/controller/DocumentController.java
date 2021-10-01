package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.KeyValueLabel;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.dto.document.DocumentData;
import az.hrportal.hrportalapi.dto.document.DocumentResponseDto;
import az.hrportal.hrportalapi.dto.document.EmployeeDocumentInformation;
import az.hrportal.hrportalapi.dto.document.PositionDocumentInformation;
import az.hrportal.hrportalapi.service.DocumentService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("document")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping(value = "export", produces = MediaType.APPLICATION_PDF_VALUE)
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public byte[] export2Pdf(@RequestParam Integer operationId, HttpServletResponse httpServletResponse) {
        byte[] response = documentService.export2Pdf(operationId);
        httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + "Erize");
        return response;
    }

    @PostMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> create(@RequestBody @Valid DocumentData documentData) {
        return ResponseDto.of(documentService.create(documentData), 200);
    }

    @PutMapping("status/{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Integer> changeStatus(@PathVariable Integer id, @RequestParam Integer status) {
        return ResponseDto.of(documentService.changeStatus(id, status));
    }

    @GetMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<List<DocumentResponseDto>> getDocuments() {
        return ResponseDto.of(documentService.getDocuments());
    }

    @GetMapping("types")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Set<KeyValueLabel<String, Integer, String>>> documentTypes() {
        return ResponseDto.of(documentService.getDocumentTypes(), 200);
    }

    @GetMapping("employee/{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<EmployeeDocumentInformation> getEmployeeInfoById(@PathVariable Integer id) {
        return ResponseDto.of(documentService.getEmployeeDocumentInfoById(id));
    }

    @GetMapping("position/{id}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<PositionDocumentInformation> getPositionInfoById(@PathVariable Integer id) {
        return ResponseDto.of(documentService.getPositionDocumentInfoById(id));
    }

    @GetMapping("position")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Set<Integer>> getAllPositionsId() {
        return ResponseDto.of(documentService.getAllPositionsId());
    }
}
