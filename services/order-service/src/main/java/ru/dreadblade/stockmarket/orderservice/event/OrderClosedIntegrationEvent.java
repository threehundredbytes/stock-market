package ru.dreadblade.stockmarket.orderservice.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.dreadblade.stockmarket.orderservice.domain.OrderType;
import ru.dreadblade.stockmarket.shared.event.IntegrationEvent;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OrderClosedIntegrationEvent extends IntegrationEvent {
    private Long stockId;
    private Long accountId;
    private String stockTicker;
    private BigDecimal pricePerStock;
    private Long quantity;
    private OrderType orderType;
    private String userId;
}
