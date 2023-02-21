package org.codeai.ecommerce.exceptions;

public class ProductValidationException extends Throwable {
  public ProductValidationException(String productNameCannotBeEmpty) {
    super(productNameCannotBeEmpty);
  }
}
