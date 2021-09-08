package az.hrportal.hrportalapi.security;

import az.hrportal.hrportalapi.error.exception.InvalidTokenException;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

public class SecurityUtil {
    public static String extractToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null) {
            token = httpServletRequest.getParameter("token");
            if (token == null) {
                throw new InvalidTokenException(token);
            } else
                return token;
        }
        if (!token.startsWith("Bearer")) {
            throw new InvalidTokenException(token);
        }
        return token.substring(7);
    }
}
