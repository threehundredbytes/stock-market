package ru.dreadblade.stockmarket.accountservice.event;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentSucceededIntegrationEvent extends IntegrationEvent {
    private Long paymentAccountId;
    private BigDecimal paymentAmount;
}
