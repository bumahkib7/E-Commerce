package org.codeai.ecommerce.requests;

import org.codeai.ecommerce.models.Customer;

import java.util.List;

public record OrderRequest(Customer customer, List<OrderItemRequest> orderItems) {
}
