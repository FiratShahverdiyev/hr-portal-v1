package az.hrportal.hrportalapi.config;

import az.hrportal.hrportalapi.config.filter.SecurityFilter;
import az.hrportal.hrportalapi.helper.i18n.LocaleMessageResolver;
import az.hrportal.hrportalapi.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${filter.url.ignore}")
    private String[] ignoredUrls;
    @Value("${filter.url.csp}")
    private String cspUrl;

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/document/status/*").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .headers()
                .contentSecurityPolicy(cspUrl)
                .and()
                .frameOptions()
                .sameOrigin()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new SecurityFilter(tokenProvider, messageResolver),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(ignoredUrls)
                .antMatchers("/auth/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private String encode(String password) {
        return getEncoder().encode(password);
    }
}
