package org.codeai.ecommerce.service;

import org.codeai.ecommerce.exceptions.CustomException;
import org.codeai.ecommerce.exceptions.ProductValidationException;
import org.codeai.ecommerce.models.Product;
import org.codeai.ecommerce.repository.ProductRepository;
import org.codeai.ecommerce.validator.ProductValidator;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> getAllProducts() {
    List<Product> productList = Streamable.of(productRepository.findAll()).toList();
    // were Performing some validation on the retrieved products
    productList.forEach(product ->
      {
        if (product.getPrice().equals(null)) {
          throw new CustomException("Product price must be greater than zero");
        } else if (product.getQuantity() <= 0) {
          throw new CustomException("Product quantity must be greater than zero");
        }
      }
    );
    return productList;
  }

  public Product createProduct(Product product) throws ProductValidationException {
    validateProduct(product);
    return productRepository.save(product);
  }

  public Product updateProduct(Product product) throws ProductValidationException {
    validateProduct(product);
    return productRepository.save(product);
  }

  public void deleteProduct(Long id) {
    if (productRepository.existsById(id)) {
      productRepository.deleteById(id);
    }
  }

  private void validateProduct(Product product) throws ProductValidationException {
    ProductValidator validator = new ProductValidator();
    validator.validate(product);
    if (product.getPrice() == null) {
      throw new ProductValidationException("Product price must be greater than zero");
    } else if (product.getQuantity() <= 0) {
      throw new ProductValidationException("Product quantity must be greater than zero");
    }
  }

  public Product getProductById(Long productId) {
    return productRepository.findById(productId).orElse(null);
  }
}
