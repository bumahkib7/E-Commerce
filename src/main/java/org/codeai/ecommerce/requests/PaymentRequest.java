package org.codeai.ecommerce.requests;

import org.codeai.ecommerce.Enums.TransactionStatus;
import org.codeai.ecommerce.models.PaymentMethod;

public record PaymentRequest(PaymentMethod paymentMethod, TransactionStatus transactionStatus,
                             java.math.BigDecimal total) {
}
