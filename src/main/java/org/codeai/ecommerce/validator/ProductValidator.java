package org.codeai.ecommerce.validator;

import org.codeai.ecommerce.exceptions.ProductValidationException;
import org.codeai.ecommerce.models.Product;

public class ProductValidator {
    public boolean isValid(Product product) {
        return product.getName() != null && product.getName().length() > 0;
    }

    public void validate(Product product) throws ProductValidationException {
        if (!isValid(product)) {
            throw new ProductValidationException("Product name cannot be empty");
        }
    }
}
