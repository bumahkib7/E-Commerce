package org.codeai.ecommerce.exceptions;

public class OrderNotFoundException extends RuntimeException {
  public OrderNotFoundException(long orderId) {
    super(String.format(
      "Order with id %d not found",
      orderId
    ));
  }
}
