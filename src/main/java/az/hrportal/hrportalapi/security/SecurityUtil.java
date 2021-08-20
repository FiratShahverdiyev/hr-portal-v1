package az.hrportal.hrportalapi.security;

public class SecurityUtil {
    public static String extractToken(String token) {
        if (token == null || !token.startsWith("Bearer")) {
            throw new RuntimeException();
        }
        return token.substring(7);
    }
}
