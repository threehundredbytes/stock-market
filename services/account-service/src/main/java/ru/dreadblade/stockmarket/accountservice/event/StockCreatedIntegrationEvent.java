package ru.dreadblade.stockmarket.accountservice.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import ru.dreadblade.stockmarket.shared.event.IntegrationEvent;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockCreatedIntegrationEvent extends IntegrationEvent {
    private Long stockId;
}
