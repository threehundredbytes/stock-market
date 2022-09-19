package ru.dreadblade.stockmarket.paymentservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {
    private Long accountId;
    private BigDecimal amount;
}
