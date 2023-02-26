package org.codeai.ecommerce.validator;

import ch.qos.logback.core.helpers.CyclicBuffer;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.codeai.ecommerce.Enums.OrderStatus;
import org.codeai.ecommerce.models.Order;
import org.codeai.ecommerce.models.OrderItems;
import org.codeai.ecommerce.models.Product;
import org.codeai.ecommerce.models.ShippingAddress;
import org.codeai.ecommerce.service.ProductService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class OrderValidator {

  private final ProductService productService;
  private CyclicBuffer<String> errors;

  public OrderValidator(ProductService productService) {
    this.productService = Objects.requireNonNull(productService, "productService must not be null");
  }

  public void validate(Order order) throws InvalidOrderException {
    validateProducts(order);
    validateShippingAddress(order);
    // add more validation methods as needed
  }

  public void validateStatusTransition(Order order, OrderStatus newStatus) throws InvalidOrderStatusTransitionException {
    OrderStatus oldStatus = order.getStatus();
    if (oldStatus == OrderStatus.CANCELLED || newStatus == OrderStatus.CANCELLED) {
      throw new InvalidOrderStatusTransitionException("Cannot transition to or from CANCELLED status");
    }
    if (oldStatus == OrderStatus.DELIVERED && newStatus != OrderStatus.RETURNED) {
      throw new InvalidOrderStatusTransitionException("Cannot transition from DELIVERED status to " + newStatus);
    }
  }

  public String getErrors(List<String> errors) {
    if (errors.isEmpty()) {
      return null;
    }
    return String.join("; ", errors);
  }

  private void validateProducts(Order order) {
    for (OrderItems item : order.getOrderItems()) {
      Product product = productService.getProductById(item.getProductId());
      if (product == null) {
        errors.add("Product with ID " + item.getProductId() + " does not exist");
      }
      assert product != null;
      if (!product.isAvailable()) {
        errors.add("Product with ID " + item.getProductId() + " is not available");
      }
      if (product.getPrice().compareTo(item.getPrice()) != 0) {
        errors.add("Price of product with ID " + item.getProductId() + " does not match");
      }
      if (product.getStock() < item.getQuantity()) {
        errors.add("Product with ID " + item.getProductId() + " does not have enough stock");
      }
    }
  }

  private void validateShippingAddress(Order order) {
    ShippingAddress address = order.getShippingAddress();
    if (StringUtils.isBlank(address.getAddressName())) {
      errors.add("Shipping address name is required");
    }
    if (StringUtils.isBlank(address.getStreet())) {
      errors.add("Shipping address street is required");
    }
    if (StringUtils.isBlank(address.getCity())) {
      errors.add("Shipping address city is required");
    }
    if (StringUtils.isBlank(address.getState())) {
      errors.add("Shipping address state is required");
    }
    if (StringUtils.isBlank(address.getCountry())) {
      errors.add("Shipping address country is required");
    }
    if (StringUtils.isBlank(address.getZipcode())) {
      errors.add("Shipping address zipcode is required");
    }
  }

  public List<String> validateOrderAndGetErrors(Order order) {
    List<String> errors = new ArrayList<>();
    validateProducts(order);
    validateShippingAddress(order);
    return errors;
  }

  public boolean isValid(Order order) {
    return false;
  }



  public String getErrors(Object[] toArray) {
    return null;
  }

  private static class InvalidOrderStatusTransitionException extends Throwable {
    public InvalidOrderStatusTransitionException(String s) {
      super(s);
    }
  }

  private static class InvalidOrderException extends Throwable {
    public InvalidOrderException(Object p0) {
      super((String) p0);
    }
  }

  // add more validation methods as needed
}
