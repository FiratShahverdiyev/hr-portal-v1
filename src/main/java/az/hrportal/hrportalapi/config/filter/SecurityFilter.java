package az.hrportal.hrportalapi.config.filter;

import az.hrportal.hrportalapi.error.ErrorCode;
import az.hrportal.hrportalapi.error.ErrorResponseDto;
import az.hrportal.hrportalapi.error.exception.InvalidTokenException;
import az.hrportal.hrportalapi.helper.i18n.LocaleMessageResolver;
import az.hrportal.hrportalapi.security.SecurityUtil;
import az.hrportal.hrportalapi.security.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class SecurityFilter extends GenericFilterBean {
    private final TokenProvider tokenProvider;
    private final LocaleMessageResolver messageResolver;

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        log.info("********** URI : {} ************", httpServletRequest.getRequestURI());
        try {
            String token = SecurityUtil.extractToken(httpServletRequest.getHeader("Authorization"));
            Authentication authentication = tokenProvider.parseAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (InvalidTokenException e) {
            log.error("---------- Security filter error . Exception ----------");
            e.printStackTrace();
            ErrorCode errorCode = ErrorCode.INTERNAL_SERVER;
            String message = messageResolver.resolve(errorCode.getMessage());
            buildHttpErrorResponse(response, message, 401);
        }
    }

    private void buildHttpErrorResponse(ServletResponse response, String message,
                                        Integer code) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        ErrorResponseDto errorResponse = new ErrorResponseDto(message, 101);
        byte[] responseToSend = (new ObjectMapper()).writeValueAsString(errorResponse).getBytes();
        httpServletResponse.setHeader("Content-Type", "application/json");
        httpServletResponse.setStatus(code);
        response.getOutputStream().write(responseToSend);
    }
}
