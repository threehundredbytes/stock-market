package ru.dreadblade.stockmarket.accountservice.event;

import lombok.Getter;
import lombok.Setter;
import ru.dreadblade.stockmarket.accountservice.event.model.PaymentStatus;
import ru.dreadblade.stockmarket.shared.event.IntegrationEvent;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentCreatedIntegrationEvent extends IntegrationEvent {
    private Long paymentAccountId;
    private BigDecimal paymentAmount;
    private PaymentStatus paymentStatus;
}
