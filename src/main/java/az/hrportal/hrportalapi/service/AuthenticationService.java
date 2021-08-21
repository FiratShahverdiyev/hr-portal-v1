package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.dto.LoginRequestDto;
import az.hrportal.hrportalapi.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public String login(LoginRequestDto loginRequestDto) {
        log.info("login service started with username : {}", loginRequestDto.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),
                loginRequestDto.getPassword());
        authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("login service completed with username : {}", loginRequestDto.getUsername());
        return tokenProvider.createToken(authentication);
    }
}
