package az.hrportal.hrportalapi.config.filter;

import az.hrportal.hrportalapi.error.ErrorCode;
import az.hrportal.hrportalapi.helper.i18n.LocaleMessageResolver;
import az.hrportal.hrportalapi.security.SecurityUtil;
import az.hrportal.hrportalapi.security.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
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

import static az.hrportal.hrportalapi.error.ErrorHandlerUtil.buildHttpErrorResponse;
import static az.hrportal.hrportalapi.error.ErrorHandlerUtil.getStackTrace;

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
            String token = SecurityUtil.extractToken(httpServletRequest);
            Authentication authentication = tokenProvider.parseAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("********** called by USERNAME : {} **********", SecurityUtil.getUsername());
            chain.doFilter(request, response);
        } catch (Exception e) {
            if (e instanceof ExpiredJwtException) {
                log.error("---------- Security filter error . Exception ---------- \n StackTrace : {}",
                        getStackTrace(e));
                ErrorCode errorCode = ErrorCode.TOKEN_EXPIRED;
                String message = messageResolver.resolve(errorCode.getMessage());
                buildHttpErrorResponse(response, message, 406);
            } else {
                log.error("---------- Security filter error . Exception ---------- \n StackTrace : {}",
                        getStackTrace(e));
                ErrorCode errorCode = ErrorCode.TOKEN_INVALID;
                String message = messageResolver.resolve(errorCode.getMessage());
                buildHttpErrorResponse(response, message, 401);
            }
        }
    }


}
