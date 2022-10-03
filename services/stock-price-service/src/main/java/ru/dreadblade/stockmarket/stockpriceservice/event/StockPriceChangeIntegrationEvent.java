package ru.dreadblade.stockmarket.stockpriceservice.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.dreadblade.stockmarket.shared.event.IntegrationEvent;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
public class StockPriceChangeIntegrationEvent extends IntegrationEvent {
    private Long stockId;
    private BigDecimal newPrice;
    private Instant changedAt;
}
