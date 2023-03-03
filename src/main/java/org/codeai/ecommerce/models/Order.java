package org.codeai.ecommerce.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.codeai.ecommerce.Enums.OrderStatus;
import org.codeai.ecommerce.Enums.TransactionStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  @ToString.Exclude
  private Customer customer;


  @Column(nullable = false)
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private ZonedDateTime orderDate;

  @Column(nullable = false)
  private BigDecimal total;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false)
  private OrderStatus status;

  @OneToOne
  @JoinColumn(name = "payment_method_id")
  private PaymentMethod paymentMethod;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private TransactionStatus paymentStatus;

  @Column(nullable = false)
  private String shippingMethod;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  @ToString.Exclude
  private List<OrderItems> orderItems = new ArrayList<>();
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinTable(name = "orders_shipping_address",
    joinColumns = @JoinColumn(name = "order_id"),
    inverseJoinColumns = @JoinColumn(name = "shipping_address_id"))
  private ShippingAddress shippingAddress;

  public Order(Customer customer,
               List<OrderItems> orderItems,
               BigDecimal total,
               PaymentMethod paymentMethod,
               String shippingMethod,
               ShippingAddress shippingAddress
               ) {
    this.customer = customer;
    this.orderItems = orderItems;
    this.total = total;
    this.quantity = orderItems.stream().mapToInt(OrderItems::getQuantity).sum();
    this.status = OrderStatus.PENDING;
    this.paymentMethod = paymentMethod;
    this.paymentStatus = TransactionStatus.PENDING;
    this.shippingMethod = shippingMethod;
    this.shippingAddress = shippingAddress;
    this.orderDate = ZonedDateTime.now();
  }

  public void addOrderItem(OrderItems orderItem) {
    orderItems.add(orderItem);
    orderItem.setOrder(this);
  }

  public void removeOrderItem(OrderItems orderItem) {
    orderItems.remove(orderItem);
    orderItem.setOrder(null);
  }

  public void removeShippingAddress(ShippingAddress shippingAddress) {
    this.shippingAddress = null;
    shippingAddress.setOrder(null);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public OrderStatus getStatus() {
    return status;
  }


  public List<OrderItems> getOrderItems() {
    return orderItems;
  }




  public ShippingAddress getShippingAddress() {
    return shippingAddress;
  }




}
