package ru.dreadblade.stockmarket.notificationservice.events;

import lombok.Getter;
import lombok.Setter;
import ru.dreadblade.stockmarket.notificationservice.events.model.OrderType;
import ru.dreadblade.stockmarket.shared.event.IntegrationEvent;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderRejectedIntegrationEvent extends IntegrationEvent {
    private Long orderId;
    private BigDecimal orderPrice;
    private OrderType orderType;
    private String stockTicker;
    private String userId;
}
