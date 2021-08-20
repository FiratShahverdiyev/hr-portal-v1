package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.LoginRequestDto;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("login")
    public ResponseDto<String> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseDto.of(authenticationService.login(loginRequestDto), 200);
    }
}
