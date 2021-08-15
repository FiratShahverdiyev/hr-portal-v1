package az.hrportal.hrportalapi.helper.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LocaleMessageResolver {

    private final MessageSource messageSource;

    public LocaleMessageResolver(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String resolve(String messageCode, Object[] arg) {
        return messageSource.getMessage(messageCode, arg, LocaleContextHolder.getLocale());
    }

    public String resolve(String messageCode) {
        return resolve(messageCode, null);
    }
}
