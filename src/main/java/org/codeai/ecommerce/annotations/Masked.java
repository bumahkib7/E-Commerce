package org.codeai.ecommerce.annotations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.codeai.ecommerce.validator.MaskedValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {MaskedValidator.class})
@Documented
public @interface Masked {
  String message() default "Invalid masked value";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String format() default "####-####-####-####";
}
