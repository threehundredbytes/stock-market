package ru.dreadblade.stockmarket.accountservice.event;

import lombok.Getter;
import lombok.Setter;
import ru.dreadblade.stockmarket.accountservice.event.model.OrderType;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderClosedIntegrationEvent extends IntegrationEvent {
    private Long stockId;
    private Long accountId;
    private BigDecimal pricePerStock;
    private Long quantity;
    private OrderType orderType;
}
