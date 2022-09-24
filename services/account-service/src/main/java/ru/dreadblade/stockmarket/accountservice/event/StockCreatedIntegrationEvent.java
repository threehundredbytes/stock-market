package ru.dreadblade.stockmarket.accountservice.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockCreatedIntegrationEvent extends IntegrationEvent {
    private Long stockId;
}