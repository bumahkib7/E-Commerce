package org.codeai.ecommerce.requests;

import org.codeai.ecommerce.models.Customer;

import java.math.BigDecimal;

public record OrderItemRequest(
    Long id,
    Customer customer,
    Integer quantity,
    Long productId,
    Long orderId,
    BigDecimal price,

    BigDecimal totalPrice
) {
}
