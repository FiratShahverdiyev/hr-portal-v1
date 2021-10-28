package az.hrportal.hrportalapi.controller;

import az.hrportal.hrportalapi.dto.LoginRequestDto;
import az.hrportal.hrportalapi.dto.RefreshTokenRequestDto;
import az.hrportal.hrportalapi.dto.ResponseDto;
import az.hrportal.hrportalapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseDto<String> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ResponseDto.of(authenticationService.login(loginRequestDto), 200);
    }

    @PostMapping("refresh")
    public ResponseDto<String> refresh(@RequestBody RefreshTokenRequestDto requestDto) {
        return ResponseDto.of(authenticationService.refresh(requestDto), 200);
    }

}
