package az.hrportal.hrportalapi.config.filter;

import az.hrportal.hrportalapi.error.ErrorCode;
import az.hrportal.hrportalapi.error.ErrorHandlerUtil;
import az.hrportal.hrportalapi.helper.i18n.LocaleMessageResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
    private final LocaleMessageResolver messageResolver;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
        ErrorHandlerUtil.buildHttpErrorResponse(response,
                messageResolver.resolve(errorCode.getMessage()), errorCode.getCode());
    }
}
