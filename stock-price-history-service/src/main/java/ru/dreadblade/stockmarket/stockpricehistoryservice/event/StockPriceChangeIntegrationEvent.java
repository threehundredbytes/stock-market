package ru.dreadblade.stockmarket.stockpricehistoryservice.event;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class StockPriceChangeIntegrationEvent extends IntegrationEvent {
    private Long stockId;
    private BigDecimal newPrice;
    private Instant changedAt;
}
