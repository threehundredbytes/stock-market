package ru.dreadblade.stockmarket.stockservice.event;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class StockPriceChangeIntegrationEvent extends IntegrationEvent {
    private Long stockId;
    private BigDecimal newPrice;
    private Instant changedAt;
}
