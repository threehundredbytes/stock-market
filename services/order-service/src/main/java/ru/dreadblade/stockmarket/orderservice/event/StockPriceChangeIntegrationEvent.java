package ru.dreadblade.stockmarket.orderservice.event;

import lombok.Getter;
import lombok.Setter;
import ru.dreadblade.stockmarket.shared.event.IntegrationEvent;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class StockPriceChangeIntegrationEvent extends IntegrationEvent {
    private Long stockId;
    private BigDecimal newPrice;
    private Instant changedAt;
}
