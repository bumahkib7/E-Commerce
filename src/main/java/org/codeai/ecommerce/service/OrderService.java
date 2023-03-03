package org.codeai.ecommerce.service;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.codeai.ecommerce.Enums.OrderStatus;
import org.codeai.ecommerce.Enums.TransactionStatus;
import org.codeai.ecommerce.exceptions.OrderNotFoundException;
import org.codeai.ecommerce.exceptions.OrderValidationException;
import org.codeai.ecommerce.models.*;
import org.codeai.ecommerce.repository.OrderRepository;
import org.codeai.ecommerce.repository.PaymentMethodRepository;
import org.codeai.ecommerce.requests.OrderRequest;
import org.codeai.ecommerce.requests.PaymentRequest;
import org.codeai.ecommerce.validator.OrderValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderService {

  private final OrderRepository orderRepository;
  private final ProductService productService;

  private final OrderItemSerivces orderItemSerivces;
  private final PaymentMethodRepository paymentMethodRepository;

  public OrderService(OrderRepository orderRepository, ProductService productService, OrderItemSerivces orderItemSerivces,
                      PaymentMethodRepository paymentMethodRepository) {
    this.orderRepository = orderRepository;
    this.productService = productService;
    this.orderItemSerivces = orderItemSerivces;
    this.paymentMethodRepository = paymentMethodRepository;
  }

  public Order createOrder(@NotNull OrderRequest orderRequest) {
    BigDecimal total = orderRequest.orderItems().stream()
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

    List<OrderItems> orderItems = orderRequest.orderItems().stream()
      .map(orderItemRequest -> {
        Product product = productService.getProductById(orderItemRequest.productId());
        int quantity = orderItemRequest.quantity();
        return new OrderItems(product, quantity);
      })
      .collect(Collectors.toList());

    Order order = new Order(orderRequest.customer(), orderItems, total, orderRequest.paymentMethod(),
      orderRequest.shippingMethod(), orderRequest.shippingAddress());
    validateOrder(order);
    return orderRepository.save(order);

  }


  @Transactional
  public Order createOrderV2(Customer customer, List<OrderItems> orderItems, PaymentMethod paymentMethod, String shippingMethod, String shippingAddress) {
    List <OrderItems> orderItemsList = orderItems.stream().map(orderItem -> {
      Product product = productService.getProductById(orderItem.getProductId());
      int quantity = orderItem.getQuantity();
      return new OrderItems(product, quantity);
    }).toList();

     BigDecimal total = orderItemsList.stream().map(OrderItems::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

     //now create a payment method variable and and if it is mobile money then create a payment request else if it is card then create a payment request
    //else set paymentstatus to Pending
    PaymentMethod paymentMethod1 = paymentMethodRepository.findById(paymentMethod.getId()).orElse(null);
    PaymentRequest paymentRequest = null;

    if (Objects.equals(Objects.requireNonNull(paymentMethod1).getMobileMoneyPayment(), "Mobile Money")) {
      paymentRequest = new PaymentRequest(paymentMethod1, TransactionStatus.PENDING, total);
    } else if (paymentMethod1.getName().equals("Card")) {
      paymentRequest = new PaymentRequest(paymentMethod1, TransactionStatus.PENDING, total);
    } else {
      paymentRequest = new PaymentRequest(paymentMethod1, TransactionStatus.PENDING, total);
    }

    // Create order

  }
public Order updateOrder(Order order) {
    validateOrder(order);
    return orderRepository.save(order);
  }

  public void deleteOrder(Long id) {
    if (orderRepository.existsById(id)) {
      orderRepository.deleteById(id);
    }
  }

  public Order getOrder(Long id) {
    validateOrder(orderRepository.findById(id).orElse(null));
    return orderRepository.findById(id).orElse(null);
  }

  public Iterable<Order> getAllOrders() throws OrderValidationException {
    validateOrder(orderRepository.findById(1L).orElse(null));
    return orderRepository.findAll();
  }

  public Order getOrderById(Long id) {
    validateOrder(orderRepository.findById(id).orElse(null));
    return orderRepository.findById(id).orElse(null);
  }

  public void updateOrderStatus(long orderId, OrderStatus newStatus) throws OrderCannotBeUpdatedException {
    Order order = orderRepository.findById(orderId)
      .orElseThrow(() -> new OrderNotFoundException(orderId));
    if (order.getStatus() == OrderStatus.CANCELLED) {
      throw new OrderCannotBeUpdatedException("Cannot update a cancelled order");
    }
    order.setStatus(newStatus);
    orderRepository.save(order);
  }


  @SneakyThrows(OrderValidationException.class)
  private void validateOrder(Order order) {
    OrderValidator validator = new OrderValidator(
      (ProductService) orderRepository.findAll().stream()
        .filter(o -> !Objects.equals(o.getId(), order.getId()))
        .collect(Collectors.toList()),
        errors);
    if (!validator.isValid(order)) {
      throw new OrderValidationException(validator.getErrors(
        order.getOrderItems().stream()
          .map(OrderItems::getProduct)
          .toList().toArray()
      ));
    }
  }

  private static class OrderCannotBeUpdatedException extends Throwable {
    public OrderCannotBeUpdatedException(String cannotUpdateACancelledOrder) {
    }
  }
}
