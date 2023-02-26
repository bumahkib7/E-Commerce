package org.codeai.ecommerce.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.codeai.ecommerce.Enums.OrderStatus;

import java.math.BigDecimal;
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
  private Date date;

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
  private String paymentStatus;

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

  public Order(Customer customer, List<OrderItems> orderItems, BigDecimal total, PaymentMethod paymentMethod, String shippingMethod, ShippingAddress shippingAddress) {
    this.customer = customer;
    this.orderItems = orderItems;
    this.total = total;
    this.quantity = orderItems.stream().mapToInt(OrderItems::getQuantity).sum();
    this.status = OrderStatus.PENDING;
    this.paymentMethod = paymentMethod;
    this.paymentStatus = String.valueOf(OrderStatus.AWAITING_PAYMENT);
    this.shippingMethod = shippingMethod;
    this.shippingAddress = shippingAddress;
    this.date = new Date();
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

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public OrderStatus getStatus() {
    return status;
  }


  public List<OrderItems> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItems> orderItems) {
    this.orderItems.clear();
    if (orderItems != null) {
      orderItems.forEach(this::addOrderItem);
    }
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
    this.status = orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = OrderStatus.valueOf(orderStatus);
    this.status = OrderStatus.valueOf(orderStatus);
  }

  public ShippingAddress getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(ShippingAddress shippingAddress) {
    this.shippingAddress = shippingAddress;
    shippingAddress.setOrder(this);
  }


}
