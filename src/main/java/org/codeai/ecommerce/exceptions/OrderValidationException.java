package org.codeai.ecommerce.exceptions;

public class OrderValidationException extends Throwable {
  public OrderValidationException(String orderIsNotValid) {
    super(OrderValidationException.class.getName() + ": " + orderIsNotValid);
  }
}
