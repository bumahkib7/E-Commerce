package org.codeai.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;
import org.codeai.ecommerce.Enums.MobileMoneyProvider;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class MobileMoneyPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MobileMoneyProvider provider;

    private String phoneNumber;


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    MobileMoneyPayment that = (MobileMoneyPayment) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
