package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.DocumentData;
import az.hrportal.hrportalapi.service.DocumentService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("document")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping(value = "export", produces = {MediaType.APPLICATION_PDF_VALUE})
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public byte[] export2Pdf(@RequestBody @Valid DocumentData documentData, HttpServletResponse httpServletResponse) {
        byte[] response = documentService.export2Pdf(documentData);
        httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + "Erize");
        return response;
    }
}