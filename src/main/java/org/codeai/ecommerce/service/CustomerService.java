package org.codeai.ecommerce.service;

import org.codeai.ecommerce.models.Customer;
import org.codeai.ecommerce.repository.CustomerRepository;
import org.codeai.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;

  private final UserRepository userRepository;

  public CustomerService(CustomerRepository customerRepository, UserRepository userRepository) {
    this.customerRepository = customerRepository;
    this.userRepository = userRepository;
  }

  public boolean isCustomer(Long userId) {
    return customerRepository.existsById(userId);
  }

  public boolean isUser(Long userId) {
    return userRepository.existsById(userId);
  }

  public Customer getCustomerById(Long userId) {
    if (!isCustomer(userId)) {
      return null;
    }
    return customerRepository.findById(userId).orElse(null);
  }
}
