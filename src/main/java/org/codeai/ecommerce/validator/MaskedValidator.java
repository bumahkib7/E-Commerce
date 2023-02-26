package org.codeai.ecommerce.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.codeai.ecommerce.annotations.Masked;

import java.lang.annotation.Annotation;

public class MaskedValidator implements ConstraintValidator<Masked, String>{

  private static final String MASKED_PATTERN = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";



  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    return value.matches(MASKED_PATTERN);
  }
}
