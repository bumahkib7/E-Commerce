package org.codeai.ecommerce.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "products")
@Getter
@Setter
@RequiredArgsConstructor
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id", nullable = false, updatable = false)
  public final long serialVersion = 23L;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private BigDecimal price;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false)
  private String image;

  @Column(nullable = false)
  private String category;


  @OneToMany
  @JoinColumn(name = "product_id")
  @ToString.Exclude
  private Set<Order> order;

  public Product(String name, BigDecimal newPrice) {
    this.name = Objects.requireNonNull(name);
    this.price = BigDecimal.valueOf(Objects.requireNonNull(newPrice).doubleValue());
  }

  public Product withPrice(BigDecimal newPrice) {
    return new Product(name, newPrice);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Product product)) return false;

    if (getSerialVersion() != product.getSerialVersion()) return false;
    if (getQuantity() != product.getQuantity()) return false;
    if (getName() != null ? !getName().equals(product.getName()) : product.getName() != null) return false;
    if (getDescription() != null ? !getDescription().equals(product.getDescription()) : product.getDescription() != null)
      return false;
    if (getPrice() != null ? !getPrice().equals(product.getPrice()) : product.getPrice() != null) return false;
    if (getImage() != null ? !getImage().equals(product.getImage()) : product.getImage() != null) return false;
    if (getCategory() != null ? !getCategory().equals(product.getCategory()) : product.getCategory() != null)
      return false;
    return getOrder() != null ? getOrder().equals(product.getOrder()) : product.getOrder() == null;
  }

  @Override
  public int hashCode() {
    int result = (int) (getSerialVersion() ^ (getSerialVersion() >>> 32));
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
    result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
    result = 31 * result + getQuantity();
    result = 31 * result + (getImage() != null ? getImage().hashCode() : 0);
    result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
    result = 31 * result + (getOrder() != null ? getOrder().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Product{" +
      "serialVersion=" + serialVersion +
      ", name='" + name + '\'' +
      ", description='" + description + '\'' +
      ", price=" + price +
      ", quantity=" + quantity +
      ", image='" + image + '\'' +
      ", category='" + category + '\'' +
      ", order=" + order +
      '}';
  }
}
