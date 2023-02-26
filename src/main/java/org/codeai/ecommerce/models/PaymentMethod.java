package org.codeai.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PaymentMethod {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @OneToOne
  @JoinColumn(name = "credit_and_debit_cards_id")
  private CreditAndDebitCards creditAndDebitCards;

  @OneToOne
  @JoinColumn(name = "mobile_money_payment_id")
  private MobileMoneyPayment mobileMoneyPayment;

}
