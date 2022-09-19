package ru.dreadblade.stockmarket.paymentservice.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class PaymentSucceededIntegrationEvent extends IntegrationEvent {
    private Long paymentAccountId;
    private BigDecimal paymentAmount;
}
