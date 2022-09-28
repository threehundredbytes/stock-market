package ru.dreadblade.stockmarket.accountservice.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.dreadblade.stockmarket.accountservice.event.model.OrderType;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OrderConfirmedIntegrationEvent extends IntegrationEvent {
    private Long orderId;
    private BigDecimal orderPrice;
    private OrderType orderType;
    private String stockTicker;
    private String userId;
}
