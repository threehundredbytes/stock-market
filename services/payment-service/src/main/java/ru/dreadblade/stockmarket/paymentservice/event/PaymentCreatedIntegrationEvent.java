package ru.dreadblade.stockmarket.paymentservice.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.dreadblade.stockmarket.paymentservice.domain.PaymentStatus;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class PaymentCreatedIntegrationEvent extends IntegrationEvent {
    private Long paymentAccountId;
    private BigDecimal paymentAmount;
    private PaymentStatus paymentStatus;
}
