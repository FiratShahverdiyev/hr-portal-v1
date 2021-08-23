package az.hrportal.hrportalapi.config.filter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CacheEvictFilter extends OncePerRequestFilter {
    private final CacheManager cacheManager;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) {
        log.info("Cache evict started");
        for (String cache : cacheManager.getCacheNames()) {
            cacheManager.getCache(cache).clear();
        }
        log.info("********** Cache evict completed **********");
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if (request.getRequestURI().equals("/auth/login")) {
            return true;
        }
        String httpMethod = request.getMethod();
        if (httpMethod.equals(HttpMethod.PUT.toString()) ||
                httpMethod.equals(HttpMethod.POST.toString()) || httpMethod.equals(HttpMethod.DELETE.toString())) {
            return false;
        }
        return true;
    }
}
