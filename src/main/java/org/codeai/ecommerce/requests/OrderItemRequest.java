package org.codeai.ecommerce.requests;

public record OrderItemRequest(Long productId, Integer quantity) {
}
