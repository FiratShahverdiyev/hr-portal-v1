package az.hrportal.hrportalapi.config.filter;

import az.hrportal.hrportalapi.security.SecurityUtil;
import az.hrportal.hrportalapi.security.TokenProvider;
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

@Slf4j
@RequiredArgsConstructor
public class SecurityFilter extends GenericFilterBean {
    private final TokenProvider tokenProvider;

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        log.info("********** URI : {} ************", httpServletRequest.getRequestURI());
        String token = SecurityUtil.extractToken(httpServletRequest.getHeader("Authorization"));
        Authentication authentication = tokenProvider.parseAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
