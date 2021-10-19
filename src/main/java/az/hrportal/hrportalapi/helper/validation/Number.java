package az.hrportal.hrportalapi.helper.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Pattern(
        regexp = "^(994)[0-9]{9}$",
        message = "validation.invalidPhone.message"
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {}
)
public @interface Number {
    String message() default "validation.invalidPhone.message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
