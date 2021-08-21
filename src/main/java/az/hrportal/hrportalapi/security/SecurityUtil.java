package az.hrportal.hrportalapi.security;

import az.hrportal.hrportalapi.error.exception.InvalidTokenException;

public class SecurityUtil {
    public static String extractToken(String token) {
        if (token == null || !token.startsWith("Bearer")) {
            throw new InvalidTokenException(token);
        }
        return token.substring(7);
    }
}
