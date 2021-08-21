package az.hrportal.hrportalapi.security;

import az.hrportal.hrportalapi.config.filter.SecurityFilter;
import az.hrportal.hrportalapi.helper.i18n.LocaleMessageResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final TokenProvider tokenProvider;
    private final LocaleMessageResolver messageResolver;

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .headers()
                .contentSecurityPolicy("script-src 'self' https://trustedscripts.example.com; " +
                        "object-src " + "https://trustedplugins.example.com; report-uri /csp-report-endpoint/")
                .and()
                .frameOptions()
                .sameOrigin()
                .and()
                .addFilterBefore(new SecurityFilter(tokenProvider, messageResolver),
                        UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/error")
                .antMatchers("/")
                .antMatchers("/csrf")
                .antMatchers("/actuator/**")
                .antMatchers("/swagger-ui.html")
                .antMatchers("/app/**/*.{js,html}")
                .antMatchers("/i18n/**")
                .antMatchers("/content/**")
                .antMatchers("/actuator/health")
                .antMatchers("/swagger-ui/index.html")
                .antMatchers("/auth/**")
                .antMatchers("/v2/api-docs", "/webjars/**")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/configuration/ui", "/configuration/security");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
//        System.out.println(encode("12345"));
        return super.authenticationManagerBean();
    }

    private String encode(String password) {
        return getEncoder().encode(password);
    }
}
