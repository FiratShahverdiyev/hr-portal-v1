package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.DocumentData;
import az.hrportal.hrportalapi.dto.KeyValue;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.service.DocumentService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("document")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping(value = "export", produces = MediaType.APPLICATION_PDF_VALUE)
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public byte[] export2Pdf(@RequestBody @Valid DocumentData documentData, HttpServletResponse httpServletResponse) {
        byte[] response = documentService.export2Pdf(documentData);
        httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + "Erize");
        return response;
    }

    @GetMapping("types")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Set<KeyValue<String, Integer>>> documentTypes() {
        return ResponseDto.of(documentService.getDocumentTypes(), 200);
    }
}
