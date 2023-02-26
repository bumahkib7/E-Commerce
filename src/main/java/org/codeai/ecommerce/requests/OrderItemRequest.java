package org.codeai.ecommerce.requests;

import java.math.BigDecimal;

public record OrderItemRequest(
    Long id,
    Integer quantity,
    Long productId,
    Long orderId,
    BigDecimal price
) {
}
