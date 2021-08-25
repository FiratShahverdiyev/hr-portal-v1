package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.service.DocumentService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("document")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping(value = "export/{file-name}")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public void export2Pdf(@PathVariable("file-name") String fileName, HttpServletResponse httpServletResponse) {
        documentService.export2Pdf(fileName, httpServletResponse);
    }
}
