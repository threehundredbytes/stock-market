package ru.dreadblade.stockmarket.stockservice.event;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
public class StockCreatedIntegrationEvent extends IntegrationEvent {
    private Long stockId;
    private BigDecimal stockPrice;
    private Instant stockCreatedAt;
}
