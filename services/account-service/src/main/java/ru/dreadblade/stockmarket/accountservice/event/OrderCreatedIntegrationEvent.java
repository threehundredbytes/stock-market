package ru.dreadblade.stockmarket.accountservice.event;

import lombok.Getter;
import lombok.Setter;
import ru.dreadblade.stockmarket.accountservice.event.model.OrderType;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderCreatedIntegrationEvent extends IntegrationEvent {
    private Long orderId;
    private Long accountId;
    private Long stockId;
    private String stockTicker;
    private Long stockQuantity;
    private BigDecimal pricePerStock;
    private OrderType orderType;
}
