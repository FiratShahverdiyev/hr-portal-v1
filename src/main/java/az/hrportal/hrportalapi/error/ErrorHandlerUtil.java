package az.hrportal.hrportalapi.error;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorHandlerUtil {
    public static String getStackTrace(Exception ex) {
        StringBuilder response = new StringBuilder();
        response.append(ex.getMessage()).append("\n");
        for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
            response.append(stackTraceElement).append("\n");
        }
        return String.valueOf(response);
    }

    public static void buildHttpErrorResponse(ServletResponse response, String message,
                                              Integer code) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        ErrorResponseDto errorResponse = new ErrorResponseDto(message, 101);
        byte[] responseToSend = (new ObjectMapper()).writeValueAsString(errorResponse).getBytes();
        httpServletResponse.setHeader("Content-Type", "application/json");
        httpServletResponse.setStatus(code);
        response.getOutputStream().write(responseToSend);
    }
}
