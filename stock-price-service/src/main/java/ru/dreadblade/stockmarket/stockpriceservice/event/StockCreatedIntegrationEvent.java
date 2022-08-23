package ru.dreadblade.stockmarket.stockpriceservice.event;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class StockCreatedIntegrationEvent extends IntegrationEvent {
    private Long stockId;
    private BigDecimal stockPrice;
    private Instant stockCreatedAt;
}
