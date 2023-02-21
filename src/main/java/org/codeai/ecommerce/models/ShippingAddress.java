package org.codeai.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "shipping_addresses")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ShippingAddress {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String address;
  @Column(nullable = false)
  private String street;

  @Column(nullable = false)
  private String city;

  @Column(nullable = false)
  private String state;

  @Column(nullable = false)
  private String country;

  @Column(nullable = false)
  private String zipCode;

  @Column(nullable = false)
  private String phone;

  @OneToOne(mappedBy = "shippingAddress")
  private Order order;


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    ShippingAddress that = (ShippingAddress) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  public CharSequence getAddressName() {
    return this.address;
  }

  public CharSequence getStreet() {
    return this.street;
  }

  public CharSequence getZipcode() {
    return this.zipCode;
  }
}
