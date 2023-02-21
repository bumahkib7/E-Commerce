package org.codeai.ecommerce.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.codeai.ecommerce.Enums.OrderStatus;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "orders")
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id", nullable = false, updatable = false)
  private final long serialVersionUID = 55L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  @ToString.Exclude
  private Customer customer;


  @Column(nullable = false)
  private Date date;

  @Column(nullable = false)
  private double total;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false)
  private OrderStatus status;

  @Column(nullable = false)
  private String paymentMethod;

  @Column(nullable = false)
  private String paymentStatus;

  @Column(nullable = false)
  private String shippingMethod;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  @ToString.Exclude
  private List<OrderItem> orderItems = new ArrayList<>();

  public Order(Customer customer, List<OrderItem> orderItems, BigDecimal totalPrice) {
    this.customer = customer;
    this.orderItems = orderItems;
    this.total = totalPrice.doubleValue();
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Order order = (Order) o;
    return true;
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
