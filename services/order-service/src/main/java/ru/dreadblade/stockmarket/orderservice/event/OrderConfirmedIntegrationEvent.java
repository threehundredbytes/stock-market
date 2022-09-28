package ru.dreadblade.stockmarket.orderservice.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderConfirmedIntegrationEvent extends IntegrationEvent {
    private Long orderId;
}
