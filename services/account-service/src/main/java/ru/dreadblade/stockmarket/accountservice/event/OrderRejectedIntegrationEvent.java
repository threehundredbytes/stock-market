package ru.dreadblade.stockmarket.accountservice.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.dreadblade.stockmarket.accountservice.event.model.OrderType;
import ru.dreadblade.stockmarket.shared.event.IntegrationEvent;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OrderRejectedIntegrationEvent extends IntegrationEvent {
    private Long orderId;
    private BigDecimal orderPrice;
    private OrderType orderType;
    private String stockTicker;
    private String userId;
}
