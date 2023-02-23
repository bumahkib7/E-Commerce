package org.codeai.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NamedEntityGraph(name = "OrderItem.product", attributeNodes = @NamedAttributeNode("product"))
public class OrderItems {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  @ToString.Exclude
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  @ToString.Exclude
  private Order order;

  @Column(nullable = false)
  private int quantity;

  public OrderItems(Product productById, Integer quantity) {
    this.product = productById;
    this.quantity = quantity;
  }


  public Long getProductId() {
    return this.product.getId();
  }

  public BigDecimal getPrice() {
    return this.product.getPrice();
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    OrderItems orderItem = (OrderItems) o;
    return id != null && Objects.equals(id, orderItem.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
