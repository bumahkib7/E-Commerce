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
  private static final long serialVersionUID = 1L;


  @OneToOne(cascade = CascadeType.ALL,
          fetch = FetchType.LAZY,
          optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(nullable = false)
  private String address;

  public Customer(User user, String address) {
    this.user = user;
    this.address = address;
  }
}
