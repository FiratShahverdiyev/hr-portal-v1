package az.hrportal.hrportalapi.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Arrays;
import java.util.Locale;

@Configuration
public class LocaleResolverConfiguration {

    public static final Locale LOCALE_AZ = new Locale("az");
    public static final Locale LOCALE_EN = new Locale("en");
    public static final Locale LOCALE_RU = new Locale("ru");


    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasename("i18n/messages");
        rs.setDefaultEncoding("UTF-8");
        return rs;
    }

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(LOCALE_AZ);
        localeResolver.setSupportedLocales(Arrays.asList(LOCALE_AZ, LOCALE_EN, LOCALE_RU));
        return localeResolver;
    }
}
