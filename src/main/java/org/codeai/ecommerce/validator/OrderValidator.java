package org.codeai.ecommerce.validator;

import org.codeai.ecommerce.exceptions.OrderValidationException;
import org.codeai.ecommerce.models.Order;
import org.codeai.ecommerce.repository.OrderRepository;

public class OrderValidator {

  private final OrderRepository orderRepository;

  public OrderValidator(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  private  boolean isValid(Order order) {
    return order.getQuantity() > 0 && orderRepository.getCustomerById() != null;
  }

  public void validate(Order order) throws OrderValidationException {
    if (!isValid(order)) {
      throw new OrderValidationException("Order is not valid");
    }
  }

}

