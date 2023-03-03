package org.codeai.ecommerce.requests;

import org.codeai.ecommerce.models.Customer;
import org.codeai.ecommerce.models.PaymentMethod;
import org.codeai.ecommerce.models.ShippingAddress;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(Customer customer,

                           List<OrderItemRequest> orderItems,

                           BigDecimal total,
                           PaymentRequest paymentRequest,
                           String shippingMethod,
                           ShippingAddress shippingAddress) {
}
