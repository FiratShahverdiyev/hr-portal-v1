package az.hrportal.hrportalapi.config.filter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Configuration
@Slf4j
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class HttpSessionFilter extends OncePerRequestFilter {
    @Value("${filter.url.ignore}")
    private String[] ignoredUrls;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        Thread.currentThread().setName(UUID.randomUUID().toString().replaceAll("-", ""));
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        for (String ignoredUrl : ignoredUrls) {
            if (request.getRequestURI().equals(ignoredUrl))
                return true;
        }
        return false;
    }
}
