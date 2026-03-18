package io.github.eduardout.e_commerce.dto.constraint.annotation;

import io.github.eduardout.e_commerce.dto.constraint.validator.CheckOnlyLetterValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static io.github.eduardout.e_commerce.dto.constraint.annotation.CheckOnlyLetters.List;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = CheckOnlyLetterValidator.class)
@Documented
@Repeatable(List.class)
public @interface CheckOnlyLetters {
    String message() default "{io.github.eduardout.e_commerce.dto.validation.CheckOnlyLetters.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        CheckOnlyLetters[] value();
    }
}
