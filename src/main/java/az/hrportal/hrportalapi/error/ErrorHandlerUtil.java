package az.hrportal.hrportalapi.error;

public class ErrorHandlerUtil {
    public static String getStackTrace(Exception ex) {
        StringBuilder response = new StringBuilder();
        response.append(ex.getMessage()).append("\n");
        for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
            response.append(stackTraceElement).append("\n");
        }
        return String.valueOf(response);
    }
}
