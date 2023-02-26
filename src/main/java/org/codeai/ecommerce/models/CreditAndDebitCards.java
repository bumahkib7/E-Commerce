package org.codeai.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.codeai.ecommerce.Enums.CreditCardType;
import org.codeai.ecommerce.annotations.Masked;
import org.codeai.ecommerce.utility.CreditCardConverter;
import org.codeai.ecommerce.utility.CreditCardNumberConverter;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "payment_methods")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CreditAndDebitCards {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @NotNull(message = "Credit card type is required")
  private CreditCardType creditCardType;

  @Column(nullable = false)
  @CreditCardNumber
  @Masked(format = "####-####-####-####")
  @Convert(converter = CreditCardNumberConverter.class)
  @NotNull(message = "Card number is required")
  private String cardNumber;

  @Column(nullable = false)
  @NotNull(message = "Card holder name is required")
  private String cardHolderName;

  @Column(nullable = false)
  @NotNull(message = "Expiration date is required")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Temporal(TemporalType.DATE)
  private Date expirationDate;

  @Column(nullable = false)
  @NotNull(message = "CVV is required")
  @Masked(format = "###")
  @Convert(converter = CreditCardConverter.class)
  private String cvv;


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    CreditAndDebitCards that = (CreditAndDebitCards) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
