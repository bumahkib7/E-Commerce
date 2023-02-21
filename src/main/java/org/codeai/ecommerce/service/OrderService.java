package org.codeai.ecommerce.service;

import lombok.SneakyThrows;
import org.codeai.ecommerce.exceptions.OrderValidationException;
import org.codeai.ecommerce.models.Order;
import org.codeai.ecommerce.models.OrderItem;
import org.codeai.ecommerce.models.Product;
import org.codeai.ecommerce.repository.OrderRepository;
import org.codeai.ecommerce.requests.OrderRequest;
import org.codeai.ecommerce.validator.OrderValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

  private final OrderRepository orderRepository;
  private final ProductService productService;

  public OrderService(OrderRepository orderRepository, ProductService productService) {
    this.orderRepository = orderRepository;
    this.productService = productService;
  }

  @SneakyThrows(OrderValidationException.class)
  public Order createOrder(OrderRequest orderRequest) {
    BigDecimal totalPrice = orderRequest.orderItems().stream()
      .map(orderItemRequest -> {
        Product product = productService.getProductById(orderItemRequest.productId());
        int quantity = orderItemRequest.quantity();
        if (product == null) {
          try {
            throw new OrderValidationException("Product does not exist for id: " + orderItemRequest.productId());
          } catch (OrderValidationException e) {
            throw new RuntimeException(e);
          }
        }
        if (quantity <= 0) {
          try {
            throw new OrderValidationException("Order item quantity must be greater than zero");
          } catch (OrderValidationException e) {
            throw new RuntimeException(e);
          }
        }
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
      })
      .reduce(BigDecimal.ZERO, BigDecimal::add);

    List<OrderItem> orderItems = orderRequest.orderItems().stream()
      .map(orderItemRequest -> new OrderItem(productService.getProductById(orderItemRequest.productId()), orderItemRequest.quantity()))
      .collect(Collectors.toList());

    Order order = new Order(orderRequest.customer(), orderItems, totalPrice);
    validateOrder(order);
    return orderRepository.save(order);
  }

  @SneakyThrows(OrderValidationException.class)
  public Order updateOrder(Order order) {
    validateOrder(order);
    return orderRepository.save(order);
  }

  public void deleteOrder(Long id) {
    if (orderRepository.existsById(id)) {
      orderRepository.deleteById(id);
    }
  }

  @SneakyThrows(OrderValidationException.class)
  public Order getOrder(Long id) {
    validateOrder(orderRepository.findById(id).orElse(null));
    return orderRepository.findById(id).orElse(null);
  }

  public Iterable<Order> getAllOrders() throws OrderValidationException {
    validateOrder(orderRepository.findById(1L).orElse(null));
    return orderRepository.findAll();
  }


  private void validateOrder(Order order) throws OrderValidationException {
    OrderValidator validator = new OrderValidator(orderRepository);
    validator.validate(order);
  }

}