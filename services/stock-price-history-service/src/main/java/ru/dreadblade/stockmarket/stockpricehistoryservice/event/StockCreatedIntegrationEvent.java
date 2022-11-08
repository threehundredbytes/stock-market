package ru.dreadblade.stockmarket.stockpricehistoryservice.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import ru.dreadblade.stockmarket.shared.event.IntegrationEvent;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockCreatedIntegrationEvent extends IntegrationEvent {
    private Long stockId;
    private BigDecimal stockPrice;
    private Instant stockCreatedAt;
}

