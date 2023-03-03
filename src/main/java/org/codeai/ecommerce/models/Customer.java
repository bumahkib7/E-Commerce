package org.codeai.ecommerce.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter
@Setter
@RequiredArgsConstructor
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @OneToOne(cascade = CascadeType.ALL,
    fetch = FetchType.LAZY,
    optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(nullable = false)
  private String address;



  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Customer customer)) return false;

    if (getUser() != null ? !getUser().equals(customer.getUser()) : customer.getUser() != null) return false;
    return getAddress() != null ? getAddress().equals(customer.getAddress()) : customer.getAddress() == null;
  }

  @Override
  public int hashCode() {
    int result = getUser() != null ? getUser().hashCode() : 0;
    result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(" +
      "address = " + address + ")";
  }
}
