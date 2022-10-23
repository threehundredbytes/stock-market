package ru.dreadblade.stockmarket.orderservice.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import ru.dreadblade.stockmarket.shared.event.IntegrationEvent;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockCreatedIntegrationEvent extends IntegrationEvent {
    private Long stockId;
    private String stockTicker;
    private BigDecimal stockPrice;
}
