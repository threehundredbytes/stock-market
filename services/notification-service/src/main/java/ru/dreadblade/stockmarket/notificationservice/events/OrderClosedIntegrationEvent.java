package ru.dreadblade.stockmarket.notificationservice.events;

import lombok.Getter;
import lombok.Setter;
import ru.dreadblade.stockmarket.notificationservice.events.model.OrderType;
import ru.dreadblade.stockmarket.shared.event.IntegrationEvent;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderClosedIntegrationEvent extends IntegrationEvent {
    private Long stockId;
    private Long accountId;
    private String stockTicker;
    private BigDecimal pricePerStock;
    private Long quantity;
    private OrderType orderType;
    private String userId;
}
