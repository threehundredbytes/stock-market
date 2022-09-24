package ru.dreadblade.stockmarket.orderservice.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockCreatedIntegrationEvent extends IntegrationEvent {
    private Long stockId;
}
