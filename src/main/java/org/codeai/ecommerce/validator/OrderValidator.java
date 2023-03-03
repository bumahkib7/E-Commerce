package org.codeai.ecommerce.validator;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class OrderValidator {



    private final ProductService productService;

    private final ArrayList<String> errors;

    public OrderValidator(ProductService productService, ArrayList<String> errors) {
        this.productService = Objects.requireNonNull(productService, "productService must not be null");
        this.errors = errors;
    }

    public void validateOrder(Order order) throws InvalidOrderStatusTransitionException {
        isValid(order);
        validateProducts(order);
        validateShippingAddress(order);
        validateStatusTransition(order, OrderStatus.PENDING);
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
                log.warn("Product with ID " + item.getProductId() + " does not exist");
            }
            assert product != null;
            if (!product.isAvailable()) {
                log.warn("Product with ID " + item.getProductId() + " is not available");
                errors.add("Product with ID " + item.getProductId() + " is not available");
            }
            if (product.getPrice().compareTo(item.getPrice()) != 0) {
                log.warn("Price of product with ID " + item.getProductId() + " does not match");
                errors.add("Price of product with ID " + item.getProductId() + "does not match");
            }
            if (product.getStock() < item.getQuantity()) {
                log.warn("Product with ID " + item.getProductId() + " does not have enough stock");
                errors.add("Product with ID " + item.getProductId() + "does not have enough stock");
            }
        }
    }

    private void validateShippingAddress(Order order) {
        ShippingAddress address = order.getShippingAddress();
        if (StringUtils.isBlank(address.getAddressName())) {
            log.warn("Shipping address name is required");
            errors.add("Shipping address name is required");
        }
        if (StringUtils.isBlank(address.getStreet())) {
            log.warn("Shipping address street is required");
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
        if(order.getOrderItems().size() == 0 ) {
            return false;
        } else if (order.getQuantity() == 0) {
            return false;
        } else if (order.getOrderDate() == null) {
            return false;
        } else {
            return true;
        }
    }



    private static class InvalidOrderStatusTransitionException extends Throwable {
        public InvalidOrderStatusTransitionException(String s) {
            super(s);
        }
    }



    // add more validation methods as needed
}
