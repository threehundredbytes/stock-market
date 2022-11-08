package ru.dreadblade.stockmarket.stockservice.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.dreadblade.stockmarket.shared.event.IntegrationEvent;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
public class StockCreatedIntegrationEvent extends IntegrationEvent {
    private Long stockId;
    private String stockTicker;
    private BigDecimal stockPrice;
    private Instant stockCreatedAt;
}
