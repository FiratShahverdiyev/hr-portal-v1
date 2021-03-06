package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.service.employee.ImageService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

   /* @PostMapping("save")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Boolean> save(Integer id, MultipartFile file) {
        imageService.save(id, file);
        return ResponseDto.of(true, 200);
    }

    @GetMapping(value = "{file-name}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] get(@PathVariable("file-name") String fileName, @RequestParam String token) {
        return imageService.get(fileName);
    }*/

    @PostMapping("save")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataType = "String")
    public ResponseDto<Boolean> s3Save(Integer id, MultipartFile file) {
        imageService.uploadS3(id, file);
        return ResponseDto.of(true, 200);
    }

    @GetMapping(value = "{file-name}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getS3(@PathVariable("file-name") String fileName, @RequestParam String token) {
        return imageService.getS3(fileName);
    }
}
