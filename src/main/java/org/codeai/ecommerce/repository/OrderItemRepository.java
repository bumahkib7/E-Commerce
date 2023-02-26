package org.codeai.ecommerce.repository;


import org.codeai.ecommerce.models.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  OrderItemRepository extends JpaRepository<OrderItems, Long> {
}
