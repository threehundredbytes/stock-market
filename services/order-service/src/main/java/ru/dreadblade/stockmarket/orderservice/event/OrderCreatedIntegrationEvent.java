package ru.dreadblade.stockmarket.orderservice.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.dreadblade.stockmarket.orderservice.domain.OrderType;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OrderCreatedIntegrationEvent extends IntegrationEvent {
    private Long orderId;
    private Long accountId;
    private Long stockId;
    private Long stockQuantity;
    private BigDecimal pricePerStock;
    private OrderType orderType;
}
